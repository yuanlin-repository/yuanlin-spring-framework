package github.yuanlin.aop.invocation;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 代理方法执行接口
 *
 * @author yuanlin
 * @date 2022/02/11/13:40
 */
public interface ProxyMethodInvocation extends MethodInvocation {

    /**
     * @return 代理对象
     */
    Object getProxy();

    /**
     * 设置执行参数
     * @param arguments 执行参数
     */
    void setArguments(Object... arguments);
}
