package github.yuanlin.aop.proxy;

import github.yuanlin.aop.AdvisedSupport;

/**
 * 创建 AopProxy 的工厂，用于生成 JdkDynamicAopProxy 和 CglibAopProxy
 *
 * @author yuanlin
 * @date 2022/02/11/12:26
 */
public interface AopProxyFactory {

    /**
     * 根据 AOP 配置信息创建代理对象
     * @param config 配置信息
     * @return 代理对象
     * @throws Throwable 如果 AOP 配置无效
     */
    AopProxy createAopProxy(AdvisedSupport config) throws RuntimeException;

}
