package github.yuanlin.beans.factory;

/**
 * FactoryBean 接口
 *
 * @author yuanlin
 * @date 2022/02/05/20:30
 */
public interface FactoryBean<T> {

    /**
     * 构造并返回 bean 实例
     * @return bean 实例
     * @throws Exception 构造失败抛出异常
     */
    T getObject() throws Exception;

    /**
     * 构造的 bean 实例的类型
     * @return bean 实例的类型
     */
    Class<?> getObjectType();
}
