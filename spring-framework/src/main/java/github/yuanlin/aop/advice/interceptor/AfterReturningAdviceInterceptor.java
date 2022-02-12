package github.yuanlin.aop.advice.interceptor;

import github.yuanlin.aop.advice.AfterAdvice;
import github.yuanlin.aop.advice.AfterReturningAdvice;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuanlin
 * @date 2022/02/11/21:55
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.advice.afterReturning(retVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return retVal;
    }
}
