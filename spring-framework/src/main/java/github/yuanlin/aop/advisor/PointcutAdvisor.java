package github.yuanlin.aop.advisor;

import github.yuanlin.aop.pointcut.Pointcut;

/**
 * @author yuanlin
 * @date 2022/02/11/12:42
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * @return 该 advisor 的 pointcut
     */
    Pointcut getPointcut();
}
