package github.yuanlin.aop.pointcut;

import github.yuanlin.aop.ClassFilter;
import github.yuanlin.aop.MethodMatcher;

/**
 * 切面抽象，切面中包含 ClassFilter 和 MethodMatcher
 *
 * @author yuanlin
 * @date 2022/02/11/11:57
 */
public interface Pointcut {

    /**
     * 获取切面的 ClassFilter
     * @return 切面的 ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * 获取切面的 MethodMatcher
     * @return 切面的 MethodMatcher
     */
    MethodMatcher getMethodMatcher();
}
