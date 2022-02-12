package github.yuanlin.advice;

import github.yuanlin.aop.advice.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author yuanlin
 * @date 2022/02/12/13:52
 */
public class LogResultAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + "方法返回：" + returnValue);
    }
}
