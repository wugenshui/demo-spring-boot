package com.mybatis.plus.mysql.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreTenant {

    /**
     * true为不做租户隔离 false为做租户隔离
     */
    boolean isIgnore() default true;
}