package github.yuanlin.beans.factory;

import github.yuanlin.beans.exception.BeansException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 提供对 BeanFactory 的集合操作
 *
 * @author yuanlin
 * @date 2022/02/05/20:33
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 检查是否有包含 beanName 的 BeanDefinition
     * @param beanName bean 的名称
     * @return BeanFactory 中是否有包含 beanName 的 BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取 BeanFactory 中的 BeanDefinition 的数量
     * @return BeanFactory 中的 BeanDefinition 的数量
     */
    int getBeanDefinitionCount();

    /**
     * 获取类型是 type 的所有 beanName
     * @param type bean 的类型
     * @return beanName 数组
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 获取所有 BeanDefinition 的名称的集合
     * @return 所有 BeanDefinition 的名称的集合
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取标注了 annotationType 的 bean 的 map(key -> value : beanName -> bean)
     * 此方法会考虑 FactoryBean
     * @param annotationType 目标注解类型
     * @return 标注了 annotationType 的 bean 的 map(key -> value : beanName -> bean)
     * @throws BeansException 如果 FactoryBean 创建 bean 失败会抛出异常
     */
    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;
}
