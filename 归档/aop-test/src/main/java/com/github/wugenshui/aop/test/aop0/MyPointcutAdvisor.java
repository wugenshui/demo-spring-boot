package com.github.wugenshui.aop.test.aop0;

import com.github.wugenshui.aop.test.aop0.annotation.DataPermission;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;

/**
 * @author : chenbo
 * @date : 2023-11-13
 */
@Component
public class MyPointcutAdvisor extends AbstractPointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(DataPermission.class, true);
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, DataPermission.class, true);
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }

    @Override
    public Advice getAdvice() {
        return new MyMethodInterceptor();
    }
}
