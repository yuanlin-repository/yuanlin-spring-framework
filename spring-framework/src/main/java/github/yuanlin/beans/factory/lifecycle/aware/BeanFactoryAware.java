package github.yuanlin.beans.factory.lifecycle.aware;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.BeanFactory;

/**
 * bean 生命周期的一环，获取 BeanFactory
 *
 * @author yuanlin
 * @date 2022/02/09/19:42
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
