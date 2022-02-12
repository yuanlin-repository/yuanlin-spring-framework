package github.yuanlin.aop;

import github.yuanlin.aop.advice.*;
import github.yuanlin.aop.advice.interceptor.AfterReturningAdviceInterceptor;
import github.yuanlin.aop.advice.interceptor.MethodBeforeAdviceInterceptor;
import github.yuanlin.aop.advice.interceptor.ThrowsAdviceInterceptor;
import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.aop.advisor.PointcutAdvisor;
import github.yuanlin.aop.targetsource.EmptyTargetSource;
import github.yuanlin.aop.advice.interceptor.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理 AOP 代理配置信息
 *
 * @author yuanlin
 * @date 2022/02/11/12:09
 */
public class AdvisedSupport {

    public static final TargetSource EMPTY_TARGET_SOURCE = EmptyTargetSource.INSTANCE;
    /**
     * bean 实例，默认为空
     */
    TargetSource targetSource = EMPTY_TARGET_SOURCE;
    /**
     * 存储当前 bean 的所有接口
     */
    private List<Class<?>> interfaces = new ArrayList<>();
    /**
     * 存储可以增强当前 bean 的所有 advisor
     */
    private List<Advisor> advisors = new ArrayList<>();
    /**
     * 缓存 key -> value : 方法名称 -> List(能够增强方法的 interceptors)
     */
    private transient Map<MethodCacheKey, List<Object>> methodCache = new ConcurrentHashMap<>();


    public void addAdvisors(Collection<Advisor> advisors) {
        if (advisors != null && !advisors.isEmpty()) {
            for (Advisor advisor : advisors) {
                this.advisors.add(advisor);
            }
        }
    }

    public void addAdvisors(Advisor... advisors) {
        addAdvisors(Arrays.asList(advisors));
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void addInterface(Class<?> ifc) {
        this.interfaces.add(ifc);
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = (targetSource != null ? targetSource : EMPTY_TARGET_SOURCE);
    }

    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    public Class<?>[] getProxiedInterfaces() {
        Class<?>[] clazz = new Class<?>[interfaces.size()];
        return this.interfaces.toArray(clazz);
    }

    public boolean isInterfacesEmpty() {
        return this.interfaces.isEmpty();
    }

    public List<Object> getInterceptors(Method method, Class<?> targetClass) {
        MethodCacheKey cacheKey = new MethodCacheKey(method);
        List<Object> cached = this.methodCache.get(cacheKey);
        if (cached == null) {
            cached = getEligibleInterceptorsForMethod(method, targetClass);
            this.methodCache.put(cacheKey, cached);
        }
        return cached;
    }

    private List<Object> getEligibleInterceptorsForMethod(Method method, Class<?> targetClass) {
        List<Advisor> advisors = getAdvisors();
        List<Object> eligibleInterceptorList = new ArrayList<>(advisors.size());
        Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                    MethodMatcher methodMatcher = pointcutAdvisor.getPointcut().getMethodMatcher();
                    if (methodMatcher.matches(method, actualClass)) {
                        List<MethodInterceptor> interceptors = getInterceptors(advisor);
                        eligibleInterceptorList.addAll(interceptors);
                    }
                }
            }
        }
        return eligibleInterceptorList;
    }

    private List<MethodInterceptor> getInterceptors(Advisor advisor) {
        List<MethodInterceptor> interceptors = new ArrayList<>(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor) {
            interceptors.add((MethodInterceptor) advice);
        }
        if (advice instanceof MethodBeforeAdvice) {
            interceptors.add((MethodInterceptor) new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice));
        } else if (advice instanceof AfterReturningAdvice) {
            interceptors.add((MethodInterceptor) new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice));
        } else if (advice instanceof ThrowsAdvice) {
            interceptors.add((MethodInterceptor) new ThrowsAdviceInterceptor(advice));
        }
        return interceptors;
    }

    private class MethodCacheKey implements Comparable<MethodCacheKey>{

        private final Method method;

        private final int hashCode;

        public MethodCacheKey(Method method) {
            this.method = method;
            this.hashCode = method.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return (this == other || (other instanceof MethodCacheKey &&
                    this.method == ((MethodCacheKey) other).method));
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return this.method.toString();
        }

        @Override
        public int compareTo(MethodCacheKey other) {
            int result = this.method.getName().compareTo(other.method.getName());
            if (result == 0) {
                result = this.method.toString().compareTo(other.method.toString());
            }
            return result;
        }
    }
}
