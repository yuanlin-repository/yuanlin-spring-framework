package github.yuanlin.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * AopProxy 接口实现类，通过 Java 动态代理创建代理对象
 *
 * @author yuanlin
 * @date 2022/02/11/12:06
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
