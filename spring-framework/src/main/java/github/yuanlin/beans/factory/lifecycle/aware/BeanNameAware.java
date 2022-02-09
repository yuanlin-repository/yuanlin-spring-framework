package github.yuanlin.beans.factory.lifecycle.aware;

/**
 * bean 生命周期的一环，获取 beanName
 *
 * @author yuanlin
 * @date 2022/02/09/19:41
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);
}