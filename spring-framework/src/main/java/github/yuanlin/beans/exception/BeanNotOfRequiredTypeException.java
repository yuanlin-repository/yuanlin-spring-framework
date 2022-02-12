package github.yuanlin.beans.exception;

/**
 * @author yuanlin
 * @date 2022/02/12/17:13
 */
public class BeanNotOfRequiredTypeException extends BeansException {
    public BeanNotOfRequiredTypeException(String msg) {
        super(msg);
    }

    public BeanNotOfRequiredTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
