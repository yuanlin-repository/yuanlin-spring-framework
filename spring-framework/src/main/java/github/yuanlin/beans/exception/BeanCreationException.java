package github.yuanlin.beans.exception;

/**
 * @author yuanlin
 * @date 2022/02/08/17:35
 */
public class BeanCreationException extends BeansException {
    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
