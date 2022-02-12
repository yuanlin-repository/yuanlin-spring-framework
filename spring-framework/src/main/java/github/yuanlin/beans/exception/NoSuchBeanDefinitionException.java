package github.yuanlin.beans.exception;

/**
 * 未找到对应 BeanDefinition 异常
 *
 * @author yuanlin
 * @date 2022/02/05/21:10
 */
public class NoSuchBeanDefinitionException extends BeansException {

    private final String beanName;

    private final Class<?> type;

    /**
     * 创建一个 NoSuchBeanDefinitionException
     * @param type 未找到 bean 的类型
     */
    public NoSuchBeanDefinitionException(Class<?> type) {
        super("No qualifying bean of type '" + type + "' available");
        this.beanName = null;
        this.type = type;
    }

    /**
     * 创建一个 NoSuchBeanDefinitionException
     * @param name 未找到 bean 的名称
     */
    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
        this.beanName = name;
        this.type = null;
    }
}
