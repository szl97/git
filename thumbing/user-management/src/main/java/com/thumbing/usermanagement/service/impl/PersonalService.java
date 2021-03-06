package com.thumbing.usermanagement.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.sql.personal.Interest;
import com.thumbing.shared.entity.sql.personal.Personal;
import com.thumbing.shared.entity.sql.user.UserInfo;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.sql.personal.IPersonalRepository;
import com.thumbing.shared.repository.sql.user.IUserInfoRepository;
import com.thumbing.shared.service.impl.BaseSqlService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.usermanagement.context.UserContextHolder;
import com.thumbing.usermanagement.dto.input.PersonalEditInput;
import com.thumbing.usermanagement.dto.input.PersonalInput;
import com.thumbing.usermanagement.dto.input.UserInfoInput;
import com.thumbing.usermanagement.dto.output.PersonalDto;
import com.thumbing.usermanagement.service.IPersonalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:40
 */
@Service
@Transactional
public class PersonalService extends BaseSqlService<Personal, IPersonalRepository> implements IPersonalService {
    @Autowired
    private IUserInfoRepository userInfoRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public Boolean createUserInfo(UserContext putUserContext) {
        UserContext userContext = UserContextHolder.getUser();
        if(!userContext.getId().equals(putUserContext.getId()) || !userContext.getName().equals(putUserContext.getName())) throw new BusinessException("数据被修改");
        UserInfoInput input = new UserInfoInput();
        input.setNickName(userContext.getName());
        return createUserInfo(userContext, input);
    }

    @Override
    public Boolean createUserInfo(UserContext userContext, UserInfoInput input){
        UserInfo userInfo = userInfoRepository.findByUserId(userContext.getId()).orElse(null);
        if(userInfo != null) return false;
        userInfo = new UserInfo();
        userInfo.setUserId(userContext.getId());
        userInfo.setUserName(userContext.getName());
        userInfo.setNickName(input.getNickName());
        userInfoRepository.save(userInfo);
        return true;
    }

    @Override
    public Boolean createPersonal(UserContext userContext, PersonalInput personalInput) {
        UserInfo userInfo = userInfoRepository.findByUserId(userContext.getId()).orElse(null);
        if(userInfo == null){
            userInfo = new UserInfo();
            userInfo.setUserId(userContext.getId());
            userInfo.setUserName(userContext.getName());
            userInfo.setNickName(personalInput.getNickName());
            userInfo = userInfoRepository.save(userInfo);
        }
        Personal personal = userInfo.getPersonal();
        if(personal != null) return false;
        personal = mapper.map(personalInput, Personal.class);
        personal.setUserId(userContext.getId());
        if(personal.getBirthDate() != null) {
            LocalDate date = personal.getBirthDate();
            personal.setBirthYear((short) date.getYear());
            personal.setBirthMonth((short) date.getMonth().getValue());
            personal.setBirthDay((short) date.getDayOfMonth());
        }
        if(CollectionUtils.isNotEmpty(personalInput.getInterests())){
           List<Interest> list =  DozerUtils.mapList(mapper, personalInput.getInterests(), Interest.class);
           Set<Interest> set = list.stream().collect(Collectors.toSet());
           personal.setInterests(set);
        }
        if(personalInput.getJob() != null){
            personal.setJobId(personalInput.getJob().getId());
        }
        if(personalInput.getOccupation() != null){
            personal.setOccupationId(personalInput.getOccupation().getId());
        }
        personal =  repository.save(personal);
        if(userInfo.getPersonalId()==null) {
            userInfo.setPersonalId(personal.getId());
            userInfoRepository.save(userInfo);
        }
        return true;
    }

    @Override
    public PersonalDto updatePersonal(UserContext userContext, PersonalEditInput input) {
        Personal personal = repository.findById(input.getId()).orElseThrow(() ->new BusinessException("个人信息不存在"));
        if(!personal.getUserId().equals(userContext.getId())) throw new BusinessException("操作错误");
        mapper.map(input, personal);
        personal.getUser().setNickName(input.getNickName());
        if(CollectionUtils.isNotEmpty(input.getInterests())){
            List<Interest> list =  DozerUtils.mapList(mapper,input.getInterests(), Interest.class);
            Set<Interest> set = list.stream().collect(Collectors.toSet());
            personal.setInterests(set);
        }
        if(input.getJob() != null){
            personal.setJobId(input.getJob().getId());
        }
        if(input.getOccupation() != null){
            personal.setOccupationId(input.getOccupation().getId());
        }
        personal =  repository.save(personal);
        return mapper.map(personal, PersonalDto.class);
    }

    @Override
    public PersonalDto fetchPersonal(UserContext userContext) {
        Personal personal = repository.findByUserId(userContext.getId()).orElse(null);
        if(personal == null){
            return null;
        }
        return mapper.map(personal, PersonalDto.class);
    }
}
