package github.yuanlin.advice;

import github.yuanlin.aop.advice.ThrowsAdvice;

/**
 * @author yuanlin
 * @date 2022/02/12/15:21
 */
public class LogThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception e) throws Throwable{
        System.out.println("发生异常: [" + e.getMessage() + "]");
    }
}
