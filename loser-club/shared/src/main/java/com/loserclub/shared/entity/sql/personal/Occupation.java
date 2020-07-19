package com.loserclub.shared.entity.sql.personal;

import com.loserclub.shared.entity.sql.BaseSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 15:59
 */
@Entity
@Table(name = "occupation")
@Getter
@Setter
@FieldNameConstants
public class Occupation extends BaseSqlEntity {
    private String name;
    private int sort;
    @OneToMany(targetEntity = Personal.class, mappedBy = Personal.Fields.job, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Personal> personals;
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Occupation)) return false;
        Occupation a = (Occupation)object;
        return  a.getId() == getId();
    }
}
