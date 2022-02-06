package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.BeanWrapper;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.config.BeanDefinition;

/**
 * bean 工厂抽象类
 *
 * @author yuanlin
 * @date 2022/02/05/20:44
 */
public abstract class AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 为 bean 注入属性值
     */
    protected void populateBean(String beanName, BeanDefinition mbd, BeanWrapper bw) {

    }

    /**
     * 调用 bean 的无参构造方法实例化 bean
     */
    protected BeanWrapper instantiateBean(String beanName, BeanDefinition mbd) {
        return null;
    }
}
