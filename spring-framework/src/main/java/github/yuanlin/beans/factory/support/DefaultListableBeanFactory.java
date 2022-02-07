package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

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
        return false;
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }
}
