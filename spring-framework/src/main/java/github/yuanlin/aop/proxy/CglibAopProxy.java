package github.yuanlin.aop.proxy;

import github.yuanlin.aop.AdvisedSupport;

/**
 * AopProxy 接口实现类，通过 Java 动态代理创建代理对象
 *
 * @author yuanlin
 * @date 2022/02/11/12:07
 */
public class CglibAopProxy implements AopProxy {

    public CglibAopProxy(AdvisedSupport config) {

    }

    @Override
    public Object getProxy() {
        return null;
    }
}
