package github.yuanlin.aop.invocation;

import github.yuanlin.aop.advice.interceptor.MethodInterceptor;
import github.yuanlin.aop.exception.AopInvocationException;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanlin
 * @date 2022/02/11/13:41
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {

    /**
     * 代理对象
     */
    protected final Object proxy;
    /**
     * 被代理的对象（原对象）
     */
    protected final Object target;
    /**
     * 增强的方法
     */
    protected final Method method;
    /**
     * 方法的参数
     */
    protected Object[] arguments;
    /**
     * 对方法进行增强的方法拦截器
     */
    protected final List<?> interceptors;
    /**
     * 拦截器下标索引
     */
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                      List<Object> interceptors) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    @Override
    public Object getProxy() {
        return proxy;
    }

    @Override
    public void setArguments(Object... arguments) {
        this.arguments = Arrays.asList(arguments).toArray();
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == interceptors.size() - 1) {
            return invokeJoinPoint();
        }
        Object interceptorOrInterceptionAdvice = this.interceptors.get(++this.currentInterceptorIndex);
        MethodInterceptor advice = (MethodInterceptor) interceptorOrInterceptionAdvice;
        return advice.invoke(this);
    }

    private Object invokeJoinPoint() throws Throwable{
        Object retVal = null;
        method.setAccessible(true);
        try {
            retVal = method.invoke(target, arguments);
        } catch (IllegalAccessException e) {
            throw new AopInvocationException("Could not access method [" + method + "]", e);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
        return retVal;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
