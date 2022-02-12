package github.yuanlin.beans.exception;

/**
 * 要获取的 bean 不是 FactoryBean，但 getBean("&...") 使用了 FactoryBean 的前缀
 *
 * @author yuanlin
 * @date 2022/02/08/17:17
 */
public class BeanIsNotAFactoryException extends BeansException {

    public BeanIsNotAFactoryException(String msg) {
        super(msg);
    }

    public BeanIsNotAFactoryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
