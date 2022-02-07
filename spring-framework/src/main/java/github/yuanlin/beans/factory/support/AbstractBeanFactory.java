package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.BeanWrapper;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.ListableBeanFactory;
import github.yuanlin.beans.factory.config.BeanDefinition;
import github.yuanlin.beans.factory.config.BeanDefinitionHolder;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * bean 工厂抽象类
 *
 * @author yuanlin
 * @date 2022/02/05/20:44
 */
public abstract class AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 存储 key -> value : beanName -> BeanDefinition
     */
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 存储所有 BeanDefinition 对应 bean 的名称
     */
    protected List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 存储 key -> value : beanName -> bean
     */
    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 存储单例 bean 的名称
     */
    protected final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /**
     * 存储早期 bean 实例（这里是用来解决循环依赖问题）
     * key -> value : beanName -> earlyBean（未完成创建的 bean 实例）
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 存储正在创建中的 bean 实例的名称
     */
    protected final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * 存储所有注册的 BeanPostProcessor
     */
    protected List<BeanPostProcessor> beanPostProcessors = new BeanPostProcessorCacheAwareList();

    public AbstractBeanFactory() {
    }

    //---------------------------------------------------------------------
    // AutowireCapableBeanFactory 接口实现
    //---------------------------------------------------------------------

    public <T> T createBean(Class<T> beanClass) throws BeansException {
        return null;
    }

    public Object initializeBean(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    public void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException {

    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    public abstract void destroySingletons();

    //---------------------------------------------------------------------
    // ListableBeanFactory 接口实现
    //---------------------------------------------------------------------

    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionNames.contains(beanName);
    }

    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    public String[] getBeanNamesForType(Class<?> type) {
        List<String> list = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanDefinitionName);
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                list.add(beanDefinitionName);
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    public String[] getBeanDefinitionNames() {
        String[] result = new String[beanDefinitionNames.size()];
        beanDefinitionNames.toArray(result);
        return result;
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return null;
    }

    //---------------------------------------------------------------------
    // BeanFactory 接口实现
    //---------------------------------------------------------------------

    public Object getBean(String name) throws BeansException {
        return null;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    /**
     * 为 bean 注入属性值
     */
    protected void populateBean(String beanName, BeanDefinition mbd, BeanWrapper bw) {

    }

    /**
     * 调用 bean 的无参构造方法实例化 bean
     */
    protected BeanWrapper instantiateBean(String beanName, BeanDefinition mbd) {
        return null;
    }

    /**
     * 注册 BeanDefinition
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinition.validate();
        beanDefinitionMap.put(beanName, beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    private class BeanPostProcessorCacheAwareList extends CopyOnWriteArrayList<BeanPostProcessor> {

        @Override
        public BeanPostProcessor set(int index, BeanPostProcessor element) {
            BeanPostProcessor result = super.set(index, element);
            return result;
        }

        @Override
        public boolean add(BeanPostProcessor o) {
            boolean success = super.add(o);
            return success;
        }

        @Override
        public void add(int index, BeanPostProcessor element) {
            super.add(index, element);
        }

        @Override
        public BeanPostProcessor remove(int index) {
            BeanPostProcessor result = super.remove(index);
            return result;
        }

        @Override
        public boolean remove(Object o) {
            boolean success = super.remove(o);
            return success;
        }
    }
}
