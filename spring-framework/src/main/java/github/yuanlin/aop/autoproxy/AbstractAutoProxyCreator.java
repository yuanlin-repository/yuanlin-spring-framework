package github.yuanlin.aop.autoproxy;

import github.yuanlin.aop.TargetSource;
import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.aop.pointcut.Pointcut;
import github.yuanlin.aop.proxy.ProxyFactory;
import github.yuanlin.aop.targetsource.SingletonTargetSource;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.BeanFactory;
import github.yuanlin.beans.factory.FactoryBean;
import github.yuanlin.beans.factory.lifecycle.InitializingBean;
import github.yuanlin.beans.factory.lifecycle.aware.Aware;
import github.yuanlin.beans.factory.lifecycle.aware.BeanFactoryAware;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;
import org.aopalliance.aop.Advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuanlin
 * @date 2022/02/11/13:16
 */
public abstract class AbstractAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    protected static final Object[] DO_NOT_PROXY = null;

    protected final Map<Object, Boolean> advisedBeans = new ConcurrentHashMap<>(256);

    protected final Map<Object, Class<?>> proxyTypes = new ConcurrentHashMap<>(16);

    protected BeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            Object cacheKey = getCacheKey(bean.getClass(), beanName);
            return wrapIfNecessary(bean, beanName, cacheKey);
        }
        return bean;
    }

    protected Object getCacheKey(Class<?> beanClass, String beanName) {
        if (beanName != null && !beanName.isEmpty()) {
            return (FactoryBean.class.isAssignableFrom(beanClass) ?
                    BeanFactory.FACTORY_BEAN_PREFIX + beanName : beanName);
        } else {
            return beanClass;
        }
    }

    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        // 如果不需要被增强
        if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
            return bean;
        }
        // 判断是否是 Advice,Pointcut,Advisor 接口的实现类
        // 如果是，跳过并缓存一下
        if (isInfrastructureClass(bean.getClass())) {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return bean;
        }
        // 获取需要对 bean 进行增强的所有 interceptor
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        if (specificInterceptors != DO_NOT_PROXY) {
            this.advisedBeans.put(cacheKey, Boolean.TRUE);
            Object proxy = createProxy(
                    bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
            this.proxyTypes.put(cacheKey, proxy.getClass());
            return proxy;
        }
        this.advisedBeans.put(cacheKey, Boolean.FALSE);
        return bean;
    }

    protected Object createProxy(Class<?> beanClass, String beanName,
                               Object[] specificInterceptors, SingletonTargetSource targetSource) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 判断 bean 是否有可以被代理的接口
        evaluateProxyInterfaces(beanClass, proxyFactory);
        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTargetSource(targetSource);
        return proxyFactory.getProxy();
    }

    private Advisor[] buildAdvisors(String beanName, Object[] specificInterceptors) {
        List<Object> allInterceptors = new ArrayList<>();
        if (specificInterceptors != null) {
            allInterceptors.addAll(Arrays.asList(specificInterceptors));
        }
        Advisor[] advisors = new Advisor[allInterceptors.size()];
        for (int i = 0; i < allInterceptors.size(); i++) {
            advisors[i] = (Advisor) allInterceptors.get(i);
        }
        return advisors;
    }

    protected void evaluateProxyInterfaces(Class<?> beanClass, ProxyFactory proxyFactory) {
        Class<?>[] interfaces = beanClass.getInterfaces();
        boolean hasReasonableProxyInterface = false;
        for (Class<?> ifc : interfaces) {
            if (!isConfigurationCallbackInterface(ifc) && ifc.getMethods().length > 0) {
                hasReasonableProxyInterface = true;
                break;
            }
        }
        if (hasReasonableProxyInterface) {
            for (Class<?> ifc : interfaces) {
                proxyFactory.addInterface(ifc);
            }
        }
    }

    private boolean isConfigurationCallbackInterface(Class<?> anInterface) {
        if (InitializingBean.class == anInterface || Aware.class.isAssignableFrom(anInterface)) {
            return true;
        }
        return false;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
        return retVal;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName,
                                                             TargetSource customTargetSource) throws BeansException;

}
