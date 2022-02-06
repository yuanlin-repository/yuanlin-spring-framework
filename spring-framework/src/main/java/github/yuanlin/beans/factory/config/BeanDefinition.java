package github.yuanlin.beans.factory.config;

/**
 * bean 配置信息
 *
 * @author yuanlin
 * @date 2022/02/05/20:42
 */
interface BeanDefinition {

    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    Boolean isSingleton();

    Boolean isPrototype();

    void setSingleton(Boolean singleton);

    Boolean isLazyInit();

    void setLazyInit(Boolean lazyInit);

    String getFactoryBeanName();

    void setFactoryBeanName(String factoryBeanName);
}
