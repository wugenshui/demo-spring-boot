package com.github.wugenshui.aop.test.aop0.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解
 * @author : chenbo
 * @date : 2023-11-13
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    /**
     * 当前类或方法是否开启数据权限
     * 即使不添加 @DataPermission 注解，默认是开启状态
     * 可通过设置 enable 为 false 禁用
     */
    boolean enable() default true;
}
