package com.thumbing.shared.service.sql;

import com.thumbing.shared.entity.sql.BaseSqlEntity;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public interface IBaseSqlService<T extends BaseSqlEntity, K extends IBaseSqlRepository<T>> {
    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAllById(Iterable<Long> iterable);

    <S extends T> List<S> saveAll(Iterable<S> iterable);

    void flush();

    <S extends T> S saveAndFlush(S s);

    void deleteInBatch(Iterable<T> iterable);

    void deleteAllInBatch();

    T getOne(long id);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    Optional<T> findOne(@Nullable Specification<T> specification);

    List<T> findAll(@Nullable Specification<T> specification);

    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable);

    List<T> findAll(@Nullable Specification<T> specification, Sort sort);

    long count(@Nullable Specification<T> specification);

    Page<T> findAll(Pageable pageable);

    <S extends T> Optional<S> findOne(Example<S> example);

    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends T> long count(Example<S> example);

    <S extends T> boolean exists(Example<S> example);

    <S extends T> S save(S s);

    Optional<T> findById(long id);

    boolean existsById(long id);

    long count();

    void deleteById(long id);

    void delete(T t);

    void deleteAll(Iterable<? extends T> iterable);

    void deleteAll();

    /**
     * 根据字段校验记录是否唯一
     *
     * @param id        实体对象的id（新增时为空）
     * @param fieldName 字段名
     * @param value     字段名对应的值
     * @return 唯一时返回true
     */
    boolean checkUnique(Long id, String fieldName, String value);

    /**
     * 比较两个列表的项目，删除不存在的项目
     *
     * @param sourceList 修改前的列表
     * @param targetList 修改后的列表
     */
    void deleteListItem(List<T> sourceList, List<T> targetList);

    /**
     * 更新列表项
     *
     * @param sourceList 数据库读取出的列表结合
     * @param targetList 接口传入的列表集合
     */
    void updateListItem(List<T> sourceList, List<T> targetList, Consumer<? super T> action);
}
