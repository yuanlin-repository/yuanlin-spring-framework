package github.yuanlin.lifecycle;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.BeanFactory;
import github.yuanlin.beans.factory.lifecycle.InitializingBean;
import github.yuanlin.beans.factory.lifecycle.aware.BeanFactoryAware;
import github.yuanlin.beans.factory.lifecycle.aware.BeanNameAware;
import github.yuanlin.context.stereotype.Component;

/**
 * 测试 bean 生命周期
 *
 * @author yuanlin
 * @date 2022/02/09/19:59
 */
@Component
public class TestLifeCycle implements BeanNameAware, BeanFactoryAware, InitializingBean {

    private String beanName;
    private BeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行 InitializingBean.afterPropertiesSet()");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("执行 BeanFactoryAware.setBeanFactory()");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("执行 BeanNameAware.setBeanName()");
    }
}
