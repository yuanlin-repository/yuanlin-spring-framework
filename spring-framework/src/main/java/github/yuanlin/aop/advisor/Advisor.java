package github.yuanlin.aop.advisor;

import github.yuanlin.aop.advice.Advice;

/**
 * @author yuanlin
 * @date 2022/02/11/12:29
 */
public interface Advisor {

    /**
     * @return 当前 Advisor 的 advice
     */
    Advice getAdvice();
}
