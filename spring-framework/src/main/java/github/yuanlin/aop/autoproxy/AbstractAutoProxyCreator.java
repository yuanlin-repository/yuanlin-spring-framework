package github.yuanlin.aop.autoproxy;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.BeanFactory;
import github.yuanlin.beans.factory.lifecycle.aware.BeanFactoryAware;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

/**
 * @author yuanlin
 * @date 2022/02/11/13:16
 */
public abstract class AbstractAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

}
