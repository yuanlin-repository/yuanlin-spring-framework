package github.yuanlin.aop.advice;

import java.lang.reflect.Method;

/**
 * @author yuanlin
 * @date 2022/02/11/12:30
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 在指定方法执行前执行
     * @param method 指定的方法
     * @param args 方法的参数
     * @param target 执行方法的实例
     * @throws Throwable 执行发生异常会抛出
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
