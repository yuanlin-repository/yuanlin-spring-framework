package github.yuanlin.aop.pointcutadvisor;

import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.aop.pointcut.Pointcut;

/**
 * @author yuanlin
 * @date 2022/02/11/12:42
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * @return 驱动该 advisor 的 pointcut
     */
    Pointcut getPointcut();
}
