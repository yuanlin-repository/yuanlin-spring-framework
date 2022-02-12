package github.yuanlin.advice;

import github.yuanlin.aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author yuanlin
 * @date 2022/02/12/13:22
 */
public class LogArgsAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("准备执行方法: " + method.getName() + ", 参数列表：" + Arrays.toString(args));
    }
}
