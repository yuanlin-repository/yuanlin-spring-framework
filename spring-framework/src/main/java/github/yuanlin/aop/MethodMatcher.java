package github.yuanlin.aop;

import java.lang.reflect.Method;

/**
 * 用于检查当前方法是否需要被 advice 增强
 *
 * @author yuanlin
 * @date 2022/02/11/11:54
 */
public interface MethodMatcher {

    /**
     * 检查当前方法是否需要被 advice 增强
     * @param method 目标方法
     * @param targetClass 目标类
     * @return method 是否需要被增强
     */
    boolean matches(Method method, Class<?> targetClass);

}
