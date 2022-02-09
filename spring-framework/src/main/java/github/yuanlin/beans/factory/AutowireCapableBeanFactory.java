package github.yuanlin.beans.factory;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.config.BeanDefinition;
import github.yuanlin.beans.factory.config.BeanDefinitionHolder;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

/**
 * bean 工厂接口（支持自动装配和对 BeanFactory 进行配置）
 * 目前只支持通过 bean 的名称进行自动装配
 *
 * @author yuanlin
 * @date 2022/02/05/20:39
 */
public interface AutowireCapableBeanFactory extends ListableBeanFactory {

    /**
     * 无自动装配
     */
    int AUTOWIRE_NO = 0;

    /**
     * 通过名称进行自动装配
     */
    int AUTOWIRE_BY_NAME = 1;

    /**
     * 通过类型进行自动装配
     */
    int AUTOWIRE_BY_TYPE = 2;

    /**
     * 创建一个新的 bean 实例
     * @param beanName bean 的名称
     * @param beanDefinition bean 的配置信息
     * @return bean 实例
     * @throws BeansException 创建 bean 失败抛出异常
     */
    Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 在这个方法中会执行 bean 生命周期的一些操作
     * 1. Aware 接口
     * 2. BeanPostProcessor 接口的 postProcessBeforeInitialization
     * 3. InitializingBean 的 AfterPropertiesSet
     * 4. BeanPostProcessor 接口的 postProcessAfterInitialization
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object initializeBean(Object existingBean, String beanName) throws BeansException;

    /**
     * 给 bean 属性注入值
     * @param existingBean bean 实例
     * @param beanName bean 的名称
     * @throws BeansException 注入属性值失败抛出此异常
     */
    void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException;

    /**
     * 为当前 bean 执行 BeanPostProcessor 接口的 postProcessBeforeInitialization 方法
     * @param existingBean bean 实例
     * @param beanName bean 的名称
     * @return 原先的 bean 或者被包装后的 bean
     * @throws BeansException 如果 postProcessBeforeInitialization 执行失败，抛出异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 为当前 bean 执行 BeanPostProcessor 接口的 postProcessAfterInitialization 方法
     * @param existingBean bean 实例
     * @param beanName bean 的名称
     * @return 原先的 bean 或者被包装后的 bean
     * @throws BeansException 如果 postProcessBeforeInitialization 执行失败，抛出异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 预初始化所有单例非懒加载 bean
     * @throws BeansException 如果存在 bean 没办法被创建则抛出异常
     */
    void preInstantiateSingletons() throws BeansException;

    /**
     * 添加 BeanPostProcessor
     * @param beanPostProcessor 要添加的 BeanPostProcessor 实例
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 注册 beanDefinition
     * @param beanName 要注册的 beanName
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 返回 BeanFactory 中的 BeanPostProcessor 数量
     * @return BeanPostProcessor 的数量
     */
    int getBeanPostProcessorCount();

    /**
     * 销毁所有的单例 bean
     */
    void destroySingletons();
}
