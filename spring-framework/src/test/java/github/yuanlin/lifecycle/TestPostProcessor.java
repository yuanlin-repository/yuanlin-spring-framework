package github.yuanlin.lifecycle;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.lifecycle.processor.BeanPostProcessor;
import github.yuanlin.context.stereotype.Component;

/**
 * @author yuanlin
 * @date 2022/02/09/20:02
 */
@Component
public class TestPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestLifeCycle) {
            System.out.println("执行 BeanPostProcessor.postProcessBeforeInitialization()");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestLifeCycle) {
            System.out.println("执行 BeanPostProcessor.postProcessAfterInitialization()");
        }
        return bean;
    }
}
