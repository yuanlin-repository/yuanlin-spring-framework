package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.BeanWrapper;
import github.yuanlin.beans.exception.BeanCreationException;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;
import github.yuanlin.beans.factory.FactoryBean;
import github.yuanlin.beans.factory.config.BeanDefinition;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanFactory 实现类
 *
 * @author yuanlin
 * @date 2022/02/05/20:44
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory {

    // TODO 初始化所有 bean
    @Override
    public void preInstantiateSingletons() throws BeansException {
        List<String> beanNames = new ArrayList<>(beanDefinitionNames);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            // 只初始化所有单例非懒加载 bean
            if (beanDefinition.isSingleton() && !beanDefinition.isLazyInit()) {
                if (isFactoryBean(beanDefinition)) {
                    getBean(FACTORY_BEAN_PREFIX + beanName);
                } else {
                    getBean(beanName);
                }
            }
        }
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanCreationException {
        // 实例化 bean
        Object instantiateBean = instantiateBean(beanName, beanDefinition);
        // 注入 bean 的属性
        populateBean(beanName, instantiateBean);
        Object exposedBean = instantiateBean;
        // bean 生命周期的一些操作 (Aware 接口， BeanPostProcessor 回调)
        exposedBean = initializeBean(exposedBean, beanName);
        return exposedBean;
    }

    private boolean isFactoryBean(BeanDefinition beanDefinition) {
        boolean isFactoryBean = FactoryBean.class.isAssignableFrom(beanDefinition.getBeanClass());
        return isFactoryBean;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public void destroySingletons() {

    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanDefinition(name).isSingleton();
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return !getBeanDefinition(name).isSingleton();
    }
}
