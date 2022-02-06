package github.yuanlin.beans.factory.lifecycle.processor;

import github.yuanlin.beans.exception.BeansException;

/**
 * BeanPostProcessor 接口
 *
 * @author yuanlin
 * @date 2022/02/05/20:42
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
