package github.yuanlin.beans.factory;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;

/**
 * bean 工厂接口
 *
 * @author yuanlin
 * @date 2022/02/05/20:28
 */
public interface BeanFactory {

    // FactoryBean 名称前缀
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 根据名称获取 bean
     * @param name bean 的名称
     * @return bean 实例
     * @throws BeansException 无法获取 bean 会抛出该异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据类型获取 bean
     * @param requiredType bean 的类型
     * @return bean 实例
     * @throws BeansException 无法获取 bean 会抛出该异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 根据 bean 的名称判断一个 bean 是否是单例
     * @param name bean 的名称
     * @return bean 是否是单例
     * @throws NoSuchBeanDefinitionException 没有这个 bean 则抛出异常
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * 根据 bean 的名称判断一个 bean 是否是多例
     * @param name bean 的名称
     * @return bean 是否是多例
     * @throws NoSuchBeanDefinitionException 没有这个 bean 则抛出异常
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;
}
