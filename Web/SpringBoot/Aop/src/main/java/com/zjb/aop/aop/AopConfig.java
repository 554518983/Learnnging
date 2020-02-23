package com.zjb.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 处理controller
 * @author zhaojianbo
 * @date 2020/2/23 11:14
 */
@Aspect
@Configuration
public class AopConfig {

    /**
     * 切面
     */
    @Pointcut("execution(public * com.zjb.aop..*.*(..))")
    public void BrokerAspect() {
    }

    /**
     * 前置通知
     */
    @Before("BrokerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("前置aspect");
    }

    /**
     * 后置通知
     */
    @After("BrokerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("后置aspect");
    }

    /**
     * 后置返回
     * @param joinPoint
     * @param ret
     */
    @AfterReturning(pointcut = "BrokerAspect()", returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        System.out.println(joinPoint.getSignature().getName() + "-----" + ret);
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("BrokerAspect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aspect around before");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("aspect around after");
        return proceed;
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "BrokerAspect()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);
    }
}
