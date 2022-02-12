package github.yuanlin.aop.proxy;

import github.yuanlin.aop.AdvisedSupport;
import github.yuanlin.aop.TargetSource;
import github.yuanlin.aop.invocation.ReflectiveMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * AopProxy 接口实现类，通过 Java 动态代理创建代理对象
 *
 * @author yuanlin
 * @date 2022/02/11/12:06
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                advised.getTargetSource().getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TargetSource targetSource = advised.getTargetSource();
        Object target = targetSource.getTarget();
        List<Object> interceptors = advised.getInterceptors(method, advised.getTargetSource().getTargetClass());

        Object result;
        if (interceptors != null && !interceptors.isEmpty()) {
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, interceptors);
            result = invocation.proceed();
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }
}
