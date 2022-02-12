package github.yuanlin.aop.autoproxy;

import github.yuanlin.aop.ClassFilter;
import github.yuanlin.aop.MethodMatcher;
import github.yuanlin.aop.TargetSource;
import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.aop.pointcut.Pointcut;
import github.yuanlin.aop.pointcutadvisor.PointcutAdvisor;
import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yuanlin
 * @date 2022/02/11/13:13
 */
public class DefaultAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {

    private BeanFactoryAdvisorRetrievalHelper advisorRetrievalHelper;

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) throws BeansException {
        // 1. helper 找所有 advisor，通过 beanfactory 创建
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        // 2. 拿到 advisor 后去和 当前类匹配，能够匹配说明当前 advisor 需要对该类做增强
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
        // 3. 拿到所有需要增强该类的 advisor 返回
        if (eligibleAdvisors.isEmpty()) {
            return DO_NOT_PROXY;
        }
        return eligibleAdvisors.toArray();
    }

    private List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }
        List<Advisor> eligibleAdvisors = new ArrayList<>();
        for (Advisor candidate : candidateAdvisors) {
            if (canApply(candidate, beanClass)) {
                eligibleAdvisors.add(candidate);
            }
        }
        return eligibleAdvisors;
    }

    private boolean canApply(Advisor advisor, Class<?> targetClass) {
        if (advisor instanceof PointcutAdvisor) {
            PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
            Pointcut pointcut = pointcutAdvisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            // 先匹配类，如果类不匹配直接返回 false
            if (!classFilter.matches(targetClass)) {
                return false;
            }
            // 获取切面的方法匹配器
            MethodMatcher methodMatcher = pointcut.getMethodMatcher();
            Set<Class<?>> classes = new LinkedHashSet<>();
            classes.addAll(getAllClassesForClassAsSet(targetClass));
            // 逐一匹配方法
            for (Class<?> clazz : classes) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (methodMatcher.matches(method, targetClass)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Set<Class<?>> getAllClassesForClassAsSet(Class<?> clazz) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        Class<?> current = clazz;
        while (current != null) {
            Class<?>[] interfaces = current.getInterfaces();
            for (Class<?> ifc : interfaces) {
                classes.add(ifc);
            }
            current = current.getSuperclass();
        }
        return classes;
    }

    private List<Advisor> findCandidateAdvisors() {
        return this.advisorRetrievalHelper.findCandidateAdvisors();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        super.setBeanFactory(beanFactory);
        advisorRetrievalHelper = new BeanFactoryAdvisorRetrievalHelper((AutowireCapableBeanFactory) beanFactory);
    }
}
