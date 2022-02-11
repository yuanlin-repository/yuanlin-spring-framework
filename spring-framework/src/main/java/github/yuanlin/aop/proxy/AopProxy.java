package github.yuanlin.aop.proxy;

/**
 * AOP 代理类接口，用于创建代理对象，目前支持 JDK 动态代理 和 CGLIB 动态代理
 *
 * @author yuanlin
 * @date 2022/02/11/12:02
 */
public interface AopProxy {

    /**
     * 创建代理对象（使用系统类加载器）
     * @return
     */
    Object getProxy();
}
