package com.github.wugenshui.aop.test.aop0;

import com.github.wugenshui.aop.test.aop0.annotation.DataPermission;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 自定义方法拦截器
 *
 * @author : chenbo
 * @date : 2023-11-13
 */
@Slf4j
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 获取注解的配置
        Method method = invocation.getMethod();
        Object targetObject = invocation.getThis();
        Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();
        DataPermission dataPermission = AnnotationUtils.findAnnotation(method, DataPermission.class);
        if (dataPermission == null) {
            dataPermission = AnnotationUtils.findAnnotation(clazz, DataPermission.class);
        }
        log.info("dataPermission.enable()=" + dataPermission.enable());


        log.info("MyMethodInterceptor：请求方法={}, 请求参数={}", invocation.getStaticPart(), invocation.getArguments());
        Object proceed = invocation.proceed();
        if (proceed instanceof String) {
            proceed = "MyMethodInterceptor：" + proceed;
        }
        log.info("MyMethodInterceptor：响应结果={}", proceed);
        return proceed;
    }
}
