package com.github.wugenshui.aop.test.aop1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author : chenbo
 * @date : 2023-11-13
 */
@Aspect
@Slf4j
@Component
public class MethodLogAspect {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 自定义 @MethodLog 切点
     */
    @Pointcut("@annotation(com.github.wugenshui.aop.test.aop1.annotation.MethodLog)")
    public void MethodLog() {
    }

    @Before("MethodLog()")
    public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
        log.info("MethodLogAspect：请求方法={}, 请求参数={}", joinPoint.getSignature(), objectMapper.writeValueAsString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "o", pointcut = "MethodLog()")
    public void doAfterReturning(Object o) throws JsonProcessingException {
        log.info("MethodLogAspect：响应结果={}", objectMapper.writeValueAsString(o));
    }
}
