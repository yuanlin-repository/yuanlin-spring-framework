package github.yuanlin.aop.exception;

/**
 * 代理执行发生异常
 *
 * @author yuanlin
 * @date 2022/02/11/21:11
 */
public class AopInvocationException extends RuntimeException {

    public AopInvocationException(String msg) {
        super(msg);
    }

    public AopInvocationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
