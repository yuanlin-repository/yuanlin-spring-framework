package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.BeanWrapper;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.ListableBeanFactory;
import github.yuanlin.beans.factory.config.BeanDefinition;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * bean 工厂抽象类
 *
 * @author yuanlin
 * @date 2022/02/05/20:44
 */
public abstract class AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 存储所有注册的 BeanPostProcessor
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

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

//    public boolean containsBeanDefinition(String beanName) {
//        return false;
//    }
//
//    public int getBeanDefinitionCount() {
//        return 0;
//    }
//
//    public String[] getBeanNamesForType(Class<?> type) {
//        return new String[0];
//    }
//
//    public String[] getBeanDefinitionNames() {
//        return new String[0];
//    }
//
//    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
//        return null;
//    }

    //---------------------------------------------------------------------
    // BeanFactory 接口实现
    //---------------------------------------------------------------------

    public Object getBean(String name) throws BeansException {
        return null;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
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
