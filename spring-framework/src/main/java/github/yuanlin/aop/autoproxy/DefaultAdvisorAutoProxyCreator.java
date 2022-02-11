package github.yuanlin.aop.autoproxy;

import github.yuanlin.aop.TargetSource;
import github.yuanlin.beans.exception.BeansException;

/**
 * @author yuanlin
 * @date 2022/02/11/13:13
 */
public class DefaultAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) throws BeansException {
        return new Object[0];
    }
}
