package github.yuanlin.aop.proxy;

import github.yuanlin.aop.AdvisedSupport;

import java.lang.reflect.Proxy;

/**
 * @author yuanlin
 * @date 2022/02/11/17:40
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) throws RuntimeException {
        if (config.isInterfacesEmpty()) {
            return new CglibAopProxy(config);
        } else {
            return new JdkDynamicAopProxy(config);
        }
    }
}
