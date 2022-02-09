package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.BeanWrapper;
import github.yuanlin.beans.exception.BeanCreationException;
import github.yuanlin.beans.exception.BeanIsNotAFactoryException;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.FactoryBean;
import github.yuanlin.beans.factory.config.BeanDefinition;
import github.yuanlin.beans.factory.config.PropertyValue;
import github.yuanlin.beans.factory.config.PropertyValues;
import github.yuanlin.beans.factory.config.RuntimeBeanReference;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
     * 存储 FactoryBean.getObject() 生成的实例
     */
    protected final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(256);

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
        return existingBean;
    }

    public void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException {
        BeanWrapper wrapper = new BeanWrapper(existingBean);
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        // 将 propertyValues 中引用类型的 value 替换为真正实例
        PropertyValues deepCopy = resolvePropertyValues(propertyValues);
        wrapper.setPropertyValues(deepCopy);
    }

    private PropertyValues resolvePropertyValues(PropertyValues propertyValues) {
        PropertyValues deepCopy = new PropertyValues();
        List<PropertyValue> propertyValueList = propertyValues.getPropertyValues();
        for (PropertyValue propertyValue : propertyValueList) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof RuntimeBeanReference) {
                RuntimeBeanReference reference = (RuntimeBeanReference) value;
                String referenceBeanName = reference.getName();
                Object referenceBean = getBean(referenceBeanName);
                deepCopy.addPropertyValue(new PropertyValue(name, referenceBean));
            } else {
                deepCopy.addPropertyValue(new PropertyValue(name, value));
            }
        }
        return deepCopy;
    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return existingBean;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return existingBean;
    }

    public abstract void destroySingletons();

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition)
            throws BeanCreationException;

    //---------------------------------------------------------------------
    // ListableBeanFactory 接口实现
    //---------------------------------------------------------------------

    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionNames.contains(beanName);
    }

    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            log.error("No bean named '" + beanName + "' found in " + this);
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return beanDefinition;
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
        return doGetBean(name, null);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    protected <T> T doGetBean(String beanName, Class<T> requiredType) {
        Object bean = null;
        // 获得 "正统的" beanName
        String transformedBeanName = transformedBeanName(beanName);
        // 从缓存中拿实例
        Object sharedInstance = getSingleton(transformedBeanName);
        // 此处三种情况
        // 1. 返回 bean
        // 2. 返回 FactoryBean
        // 3. 返回 FactoryBean.getObject() 生成的 bean
        if (sharedInstance != null) {
            // 如果有则直接返回
            bean = getObjectForBeanInstance(sharedInstance, beanName, transformedBeanName);
        } else {
            try {
                BeanDefinition beanDefinition = getBeanDefinition(transformedBeanName);
                if (beanDefinition.isSingleton()) {
                    // getSingleton 锁保证线程安全 注册单例到缓存
                    sharedInstance = getSingletonObject(transformedBeanName, beanDefinition);
                    bean = getObjectForBeanInstance(sharedInstance, beanName, transformedBeanName);
                } else if (beanDefinition.isPrototype()) {
                    // 多例
                    Object prototypeInstance = createBean(transformedBeanName, beanDefinition);
                    bean = getObjectForBeanInstance(prototypeInstance, beanName, transformedBeanName);
                }
            } catch (Exception e) {
                log.error("create bean error! beanName: [{}]", beanName);
            }
        }
        // 类型判断

        return (T) bean;
    }

    /**
     * 创建单例对象
     */
    private Object getSingletonObject(String beanName, BeanDefinition beanDefinition) {
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                singletonObject = createBean(beanName, beanDefinition);
                addSingleton(beanName, singletonObject);
            }
            return singletonObject;
        }
    }

    private void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName, String transformedBeanName) {
        // 要获取的是 FactoryBean (beanName 加上了前缀 &)
        if (isFactoryDereference(beanName)) {
            if (!(beanInstance instanceof FactoryBean)) {
                throw new BeanIsNotAFactoryException(
                        "BeanInstance class: " + '[' + beanInstance.getClass() + ']' + "target class: "+ '[' + FactoryBean.class.getName() + ']');
            }
            return beanInstance;
        }
        // 普通 bean
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        // 获取的是 FactoryBean 实例
        Object object = factoryBeanObjectCache.get(transformedBeanName);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, transformedBeanName);
        }
        return object;
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object;
        if (factoryBean.isSingleton()) {
            // 单例
            synchronized (singletonObjects) {
                object = this.factoryBeanObjectCache.get(beanName);
                if (object == null) {
                    object = doGetObjectFromFactoryBean(factoryBean, beanName);
                }
                Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
                if (alreadyThere != null) {
                    object = alreadyThere;
                } else {
                    // BeanPostProcessor 后置处理
                    object = postProcessObjectFromFactoryBean(object, beanName);
                }
                if (containsSingleton(beanName)) {
                    this.factoryBeanObjectCache.put(beanName, object);
                }
            }
        } else {
            // 多例
            object = doGetObjectFromFactoryBean(factoryBean, beanName);
            // BeanPostProcessor 后置处理
            object = postProcessObjectFromFactoryBean(object, beanName);
        }
        return object;
    }

    private boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) {
        return applyBeanPostProcessorsAfterInitialization(object, beanName);
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object;
        try {
            object = factoryBean.getObject();
        } catch (Exception e) {
            throw new BeanCreationException("FactoryBean threw exception on object creation: beanName[" + beanName + "]", e);
        }
        return object;
    }

    private boolean isFactoryDereference(String beanName) {
        return (beanName != null && beanName.startsWith(FACTORY_BEAN_PREFIX));
    }


    private Object getSingleton(String beanName) {
        if (singletonObjects.containsKey(beanName)) {
            return singletonObjects.get(beanName);
        }
        return null;
    }

    private String transformedBeanName(String beanName) {
        if (beanName.startsWith(FACTORY_BEAN_PREFIX)) {
            return beanName.substring(FACTORY_BEAN_PREFIX.length());
        }
        return beanName;
    }



    /**
     * 为 bean 注入属性值
     */
    protected void populateBean(String beanName, Object instance) {
        applyBeanPropertyValues(instance, beanName);
    }

    /**
     * 调用 bean 的无参构造方法实例化 bean
     */
    protected Object instantiateBean(String beanName, BeanDefinition beanDefinition) {
        Object newInstance = null;
        try {
            newInstance = beanDefinition.getBeanClass().newInstance();
        } catch (Exception e) {
            log.error("instantiate bean error, beanName: [{}]", beanName, e);
            throw new BeanCreationException("instantiate bean error, beanName:" + beanName, e);
        }
        return newInstance;
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
