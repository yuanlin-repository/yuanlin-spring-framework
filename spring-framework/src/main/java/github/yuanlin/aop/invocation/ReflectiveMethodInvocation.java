package github.yuanlin.aop.invocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author yuanlin
 * @date 2022/02/11/13:41
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {
    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public void setArguments(Object... arguments) {

    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Object proceed() throws Throwable {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }
}
