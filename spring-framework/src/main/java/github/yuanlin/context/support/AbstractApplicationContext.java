package github.yuanlin.context.support;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.exception.NoSuchBeanDefinitionException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;
import github.yuanlin.beans.factory.support.DefaultListableBeanFactory;
import github.yuanlin.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring 应用程序上下文抽象类（使用模板方法模式）
 *
 * @author yuanlin
 * @date 2022/02/05/20:55
 */
@Slf4j
public abstract class AbstractApplicationContext implements ApplicationContext {

    /**
     * BeanFactory
     */
    protected DefaultListableBeanFactory beanFactory;

    /**
     * 保证 refresh 和 destroy 方法执行时的线程安全
     */
    private final Object startupShutdownMonitor = new Object();

    /**
     * 刷新容器
     */
    public void refresh() {
        // 保证线程安全，同一时间只能有一个线程进行容器的 refresh 或 destroy
        synchronized (this.startupShutdownMonitor) {
            log.info("start refreshing!");
            // 创建并刷新 BeanFactory
            AutowireCapableBeanFactory beanFactory = obtainFreshBeanFactory();
            // 注册所有 BeanPostProcessor
            registerBeanPostProcessors(beanFactory);
            // 初始化所有单例非懒加载的 bean
            finishBeanFactoryInitialization(beanFactory);
        }
    }

    protected AutowireCapableBeanFactory obtainFreshBeanFactory() {
        // 刷新 BeanFactory，由子类实现这个方法
        // 功能是: 通过读取 xml 配置文件或者扫描注解来加载注册 BeanDefinition
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        // 获取所有 BeanPostProcessor 的名称
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
        for (String ppName : postProcessorNames) {
            BeanPostProcessor bean = beanFactory.getBean(ppName, BeanPostProcessor.class);
            beanPostProcessors.add(bean);
        }
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            log.info("register beanPostProcessor: [{}]", beanPostProcessor);
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    protected void finishBeanFactoryInitialization(AutowireCapableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    //---------------------------------------------------------------------
    // BeanFactory 接口实现
    //---------------------------------------------------------------------

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isPrototype(name);
    }


    //---------------------------------------------------------------------
    // ListableBeanFactory 接口实现
    //---------------------------------------------------------------------

    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return getBeanFactory().getBeansWithAnnotation(annotationType);
    }

    /**
     * 获取当前上下文的 BeanFactory
     * @return 当前上下文的 BeanFactory
     * @throws IllegalStateException 如果 BeanFactory 为 null，或者没有 BeanFactory，抛出异常
     */
    public AutowireCapableBeanFactory getBeanFactory() throws IllegalStateException {
        if (this.beanFactory != null) {
            return this.beanFactory;
        } else {
            throw new IllegalStateException("no beanFactory exists");
        }
    }

    /**
     * 刷新 BeanFactory，注册 BeanDefinitions 到 BeanFactory 中
     * @throws BeansException
     * @throws IllegalStateException
     */
    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

}
