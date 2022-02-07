package github.yuanlin.beans.factory.config;

/**
 * bean 配置信息
 *
 * @author yuanlin
 * @date 2022/02/05/20:42
 */
public interface BeanDefinition {

    Class<?> getBeanClass();

    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    Boolean isSingleton();

    Boolean isPrototype();

    void setSingleton(Boolean singleton);

    Boolean isLazyInit();

    void setLazyInit(Boolean lazyInit);

    String getFactoryBeanName();

    void setFactoryBeanName(String factoryBeanName);

    PropertyValues getPropertyValues();

    void setPropertyValues(PropertyValues propertyValues);

    boolean validate();
}
