package github.yuanlin.beans.exception;

/**
 * 根据 beanName 获取的 bean 与指定类型不匹配异常
 *
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
