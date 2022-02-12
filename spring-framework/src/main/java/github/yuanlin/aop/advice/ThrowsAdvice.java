package github.yuanlin.aop.advice;

/**
 * 异常通知
 *
 * @author yuanlin
 * @date 2022/02/11/12:31
 */
public interface ThrowsAdvice extends AfterAdvice {

    // void afterThrowing([method, args, target], ThrowableSubclass)
    // public void afterThrowing(Exception e)
    // public void afterThrowing(Method method, Object[] args, Object target, Exception ex)

}
