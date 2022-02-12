package github.yuanlin.aop.advice;

import java.lang.reflect.Method;

/**
 * 方法返回通知
 *
 * @author yuanlin
 * @date 2022/02/11/12:30
 */
public interface AfterReturningAdvice extends AfterAdvice {

    /**
     * 在指定方法返回结果后执行
     * @param returnValue 方法返回的结果
     * @param method 指定的方法
     * @param args 方法的参数
     * @param target 执行方法的实例
     * @throws Throwable 执行发生异常会抛出
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
