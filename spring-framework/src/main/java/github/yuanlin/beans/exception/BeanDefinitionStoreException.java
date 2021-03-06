package github.yuanlin.beans.exception;

/**
 * 存储 BeanDefinition 异常
 *
 * @author yuanlin
 * @date 2022/02/06/20:31
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
