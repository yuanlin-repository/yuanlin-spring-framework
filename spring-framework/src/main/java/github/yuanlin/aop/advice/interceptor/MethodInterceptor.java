package github.yuanlin.aop.advice.interceptor;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuanlin
 * @date 2022/02/11/20:32
 */
public interface MethodInterceptor extends interceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;
}
