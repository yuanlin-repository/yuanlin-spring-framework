package github.yuanlin.aop.invocation;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuanlin
 * @date 2022/02/11/13:40
 */
public interface ProxyMethodInvocation extends MethodInvocation {

    Object getProxy();

    void setArguments(Object... arguments);
}
