package github.yuanlin.beans.factory.config;

/**
 * @author yuanlin
 * @date 2022/02/05/21:49
 */
public class RootBeanDefinition implements BeanDefinition {

    /**
     * bean 的类型
     */
    private Class<?> beanClass;
    /**
     * bean 的全限定类名
     */
    private String beanClassName;
    /**
     * 是否单例
     */
    private Boolean singleton;
    /**
     * 是否懒加载
     */
    private Boolean lazyInit;
    /**
     * 该 bean 是 FactoryBean，其 FactoryBean 的名称
     */
    private String factoryBeanName;
    /**
     * 该 bean 需要注入的属性名称和注入的值
     */
    private PropertyValues propertyValues;

    public RootBeanDefinition() {
        propertyValues = new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName == null ? this.beanClassName : "";
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public Boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public Boolean isPrototype() {
        return !this.singleton;
    }

    @Override
    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }

    @Override
    public Boolean isLazyInit() {
        return this.lazyInit;
    }

    @Override
    public void setLazyInit(Boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName == null ? this.factoryBeanName : "";
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    @Override
    public PropertyValues getPropertyValues() {
        return this.propertyValues;
    }

    @Override
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public boolean validate() {
        if (beanClassName != null) {
            return true;
        }
        return false;
    }
}
