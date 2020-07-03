package com.loserclub.pushdata.common.jpa;

import java.lang.annotation.*;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryOperator {
    Operator value() default Operator.EQ;
    String fieldName() default "";
}