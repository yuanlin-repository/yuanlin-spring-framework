package github.yuanlin.aop.advice.interceptor;

import github.yuanlin.aop.advice.AfterAdvice;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法抛出异常拦截器
 *
 * @author yuanlin
 * @date 2022/02/11/21:55
 */
@Slf4j
public class ThrowsAdviceInterceptor implements AfterAdvice, MethodInterceptor {

    private static final String AFTER_THROWING = "afterThrowing";

    private final Object throwsAdvice;

    private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();

    public ThrowsAdviceInterceptor(Object advice) {
        this.throwsAdvice = advice;
        Method[] methods = throwsAdvice.getClass().getMethods();
        // ThrowsAdvice 没有定义方法
        // 默认解析两种方法:（其中 Exception 可以是其子类）
        // public void afterThrowing(Exception e)
        // public void afterThrowing(Method method, Object[] args, Object target, Exception ex)
        for (Method method : methods) {
            if (method.getName().equals(AFTER_THROWING) &&
                method.getParameterCount() == 1 || method.getParameterCount() == 4) {
                Class<?> throwableParam = method.getParameterTypes()[method.getParameterCount() - 1];
                if (Throwable.class.isAssignableFrom(throwableParam)) {
                    // 异常处理器
                    this.exceptionHandlerMap.put(throwableParam, method);
                    log.info("Found exception handler method on throws advice: [{}], method: [{}]", advice, method);
                }
            }
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }
        catch (Throwable ex) {
            Method handlerMethod = getExceptionHandler(ex);
            if (handlerMethod != null) {
                invokeHandlerMethod(invocation, ex, handlerMethod);
            }
            throw ex;
        }
    }

    private void invokeHandlerMethod(MethodInvocation invocation, Throwable exception, Method handler) throws Throwable {
        Object[] handlerArgs;
        if (handler.getParameterCount() == 1) {
            handlerArgs = new Object[] {exception};
        } else {
            handlerArgs = new Object[] {
                    invocation.getMethod(), invocation.getArguments(), invocation.getThis(), exception
            };
        }
        try {
            handler.invoke(this.throwsAdvice, handlerArgs);
        } catch (InvocationTargetException targetEx) {
            throw targetEx.getTargetException();
        }
    }

    private Method getExceptionHandler(Throwable exception) {
        Class<?> clazz = exception.getClass();
        Method handler = this.exceptionHandlerMap.get(clazz);
        while (handler == null && clazz != Throwable.class) {
            clazz = clazz.getSuperclass();
            handler = this.exceptionHandlerMap.get(clazz);
        }
        if (handler != null) {
            log.info("find handler for exception of type: [{}]", clazz);
        }
        return handler;
    }
}
