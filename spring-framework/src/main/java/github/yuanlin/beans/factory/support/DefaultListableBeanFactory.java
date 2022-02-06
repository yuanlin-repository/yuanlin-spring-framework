package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;
import github.yuanlin.beans.factory.config.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 实现类
 *
 * @author yuanlin
 * @date 2022/02/05/20:44
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory {

    /**
     * 存储 key -> value : beanName -> BeanDefinition
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 存储所有 BeanDefinition 对应 bean 的名称
     */
    private List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 存储 key -> value : beanName -> bean
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 存储单例 bean 的名称
     */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /**
     * 存储早期 bean 实例（这里是用来解决循环依赖问题）
     * key -> value : beanName -> earlyBean（未完成创建的 bean 实例）
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 存储正在创建中的 bean 实例的名称
     */
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));


    public void destroySingletons() {

    }

    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    public int getBeanDefinitionCount() {
        return 0;
    }

    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return null;
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }
}
