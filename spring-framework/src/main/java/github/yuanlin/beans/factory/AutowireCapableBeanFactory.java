package github.yuanlin.beans.factory;

import github.yuanlin.beans.exception.BeansException;

/**
 * bean 工厂接口（支持自动装配）
 * 目前只支持通过 bean 的名称进行自动装配
 *
 * @author yuanlin
 * @date 2022/02/05/20:39
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

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
     * @param beanClass bean 的类型
     * @return bean 实例
     * @throws BeansException 实例化失败或者依赖注入失败
     */
    <T> T createBean(Class<T> beanClass) throws BeansException;

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
}
