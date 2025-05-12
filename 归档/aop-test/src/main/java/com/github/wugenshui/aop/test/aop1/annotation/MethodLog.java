package com.github.wugenshui.aop.test.aop1.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. 定义切点
 * 2. 编写切面方法 MethodLogAspect
 *
 * @author : chenbo
 * @date : 2023-11-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MethodLog {

}
