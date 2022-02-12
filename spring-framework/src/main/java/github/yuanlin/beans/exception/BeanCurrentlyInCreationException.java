package github.yuanlin.beans.exception;

/**
 * bean 正在创建中异常
 *
 * @author yuanlin
 * @date 2022/02/09/16:59
 */
public class BeanCurrentlyInCreationException extends BeansException {
    public BeanCurrentlyInCreationException(String msg) {
        super(msg);
    }

    public BeanCurrentlyInCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
