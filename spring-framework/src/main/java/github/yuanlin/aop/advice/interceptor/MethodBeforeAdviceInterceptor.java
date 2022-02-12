package github.yuanlin.aop.advice.interceptor;

import github.yuanlin.aop.advice.BeforeAdvice;
import github.yuanlin.aop.advice.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuanlin
 * @date 2022/02/11/20:32
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {

    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
