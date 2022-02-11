package github.yuanlin.aop.proxy;

import github.yuanlin.aop.AdvisedSupport;
import lombok.SneakyThrows;

/**
 * @author yuanlin
 * @date 2022/02/11/16:59
 */
public class ProxyFactory extends AdvisedSupport  {

    private AopProxyFactory aopProxyFactory;

    public ProxyFactory() {
        aopProxyFactory = new DefaultAopProxyFactory();
    }

    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private final synchronized AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(this);
    }
}
