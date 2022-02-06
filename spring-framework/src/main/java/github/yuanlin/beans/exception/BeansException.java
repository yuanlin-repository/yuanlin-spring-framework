package github.yuanlin.beans.exception;

/**
 * beans 包下所有异常的抽象类
 *
 * @author yuanlin
 * @date 2022/02/05/21:05
 */
public abstract class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
