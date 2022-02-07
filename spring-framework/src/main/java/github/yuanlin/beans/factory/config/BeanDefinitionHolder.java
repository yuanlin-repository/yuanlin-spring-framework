package github.yuanlin.beans.factory.config;

/**
 * 存储 beanDefinition 和 beanName
 *
 * @author yuanlin
 * @date 2022/02/07/16:47
 */
public class BeanDefinitionHolder {

    private String beanName;

    private BeanDefinition beanDefinition;

    public BeanDefinitionHolder(String beanName, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.beanDefinition = beanDefinition;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public void setBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
    }
}
