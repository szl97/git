package com.thumbing.auth.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.auth.cache.FailureLoginCache;
import com.thumbing.auth.cache.PasswordChangeCache;
import com.thumbing.auth.context.LoginUserContextHolder;
import com.thumbing.auth.dto.input.LoginRequest;
import com.thumbing.auth.service.IAuthService;
import com.thumbing.shared.auth.authentication.AuthorizationContextHolder;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.auth.permission.PermissionCache;
import com.thumbing.shared.auth.permission.SkipPathRequestMatcher;
import com.thumbing.shared.entity.sql.system.Device;
import com.thumbing.shared.entity.sql.system.User;
import com.thumbing.shared.exception.AccountLockException;
import com.thumbing.shared.exception.UserContextException;
import com.thumbing.shared.exception.UserLoginException;
import com.thumbing.shared.jwt.JwtTokenFactory;
import com.thumbing.shared.jwt.extractor.JwtHeaderTokenExtractor;
import com.thumbing.shared.repository.sql.system.IUserRepository;
import com.thumbing.shared.thread.CustomThreadPool;
import com.thumbing.shared.utils.user.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 9:52
 */
@Service
@Slf4j
@Transactional
public class AuthService implements IAuthService {
    private final Integer MAX_FAILURE_TIMES = 5;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private SkipPathRequestMatcher skipPathRequestMatcher;
    @Autowired
    private PermissionCache permissionCache;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepository repository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private FailureLoginCache failureLoginCache;
    @Autowired
    private PasswordChangeCache passwordChangeCache;
    @Autowired
    private CustomThreadPool customThreadPool;


    @Override
    public boolean auth(String authorization, String applicationName, String url) {
        if(ignoreAuthentication(applicationName, url))  return true;
        return hasPermission(authorization, applicationName, url);
    }

    @Override
    public boolean auth(String userName) {
        String authorization = AuthorizationContextHolder.getAuthorization();
        UserContext userContext = tokenUtils.getUserContext(authorization);
        if (userContext == null) {
            //TODO:抛出gateway可识别得异常
            throw new UserContextException("尚未登陆");
        }
        if(passwordChangeCache.exist(userContext.getId())){
            LocalDateTime changeTime = passwordChangeCache.getChangeTime(userContext.getId());
            Date time = tokenUtils.getTokenCreationTime(authorization);
            Date change = Date.from(changeTime.atZone(ZoneId.systemDefault()).toInstant());
            if(change.after(time)){
                throw new UserContextException("登录过期");
            }
        }
        return userContext.getName().equals(userName);
    }

    @Override
    public User checkAndGetUser(String userName, String password) {
        if(failureLoginCache.getFailureTimes(userName) >= MAX_FAILURE_TIMES){
            throw new AccountLockException("登录失败次数过多，账户已冻结");
        }
        User user = repository.findByUserName(userName).orElseThrow(()-> new UsernameNotFoundException("用户名不存在"));
        if(!passwordEncoder.matches(password, user.getPassword())) throw new UserLoginException("密码错误");
        return user;
    }

    @Override
    public void succeedLogin(LoginRequest loginRequest) {
        failureLoginCache.clear(loginRequest.getUserName());
        User user = LoginUserContextHolder.getLoginUser();
        if(user.isFirst()){
            user.setFirst(false);
        }
        if(user != null) {
            customThreadPool.submit(
                    ()->{
                        if (user.getLastLogin().toLocalDate()
                                .equals(LocalDate.now().minusDays(1))) {
                            user.setContinueDays(user.getContinueDays() + 1);
                        }
                        else if(user.getLastLogin().toLocalDate().equals(LocalDate.now())){
                            user.setContinueDays(1);
                        }
                        user.setLastLogin(LocalDateTime.now());
                        if (loginRequest.getDeviceInput() != null) {
                            Device device = mapper.map(
                                    loginRequest.getDeviceInput(),
                                    Device.class);
                            user.getDevices().add(device);
                            user.setCurrentDevice(device);
                        }
                        repository.save(user);
                    }
            );
        }
    }

    @Override
    public void failLogin(LoginRequest loginRequest) {
        failureLoginCache.increment(loginRequest.getUserName());
    }

    /**
     * 是否跳过权限验证
     * @param url
     * @return
     */
    private boolean ignoreAuthentication(String applicationName, String url) {
        return skipPathRequestMatcher.matches(applicationName, url);
    }

    /**
     * 是否有访问权限
     * @param authorization
     * @param applicationName
     * @param url
     * @return
     */
    private boolean hasPermission(String authorization, String applicationName, String url) {
        UserContext userContext = tokenUtils.getUserContext(authorization);
        if (userContext == null) {
            //TODO:抛出gateway可识别得异常
            throw new UserContextException("尚未登陆");
        }
        String urlPermission = permissionCache.getUrlPermission(applicationName, url);
        GrantedAuthority needAuthority = new SimpleGrantedAuthority(urlPermission);
        List<GrantedAuthority> authorities = userContext.getAuthorities();
        return authorities.contains(needAuthority);
    }

}
