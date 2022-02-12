package github.yuanlin.aop.autoproxy;

import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 BeanFactory 中获取所有 advisor（实现了 Advisor 接口）
 *
 * @author yuanlin
 * @date 2022/02/12/16:57
 */
public class BeanFactoryAdvisorRetrievalHelper {

    private AutowireCapableBeanFactory beanFactory;

    private volatile String[] cachedAdvisorBeanNames;

    public BeanFactoryAdvisorRetrievalHelper(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public List<Advisor> findCandidateAdvisors() {
        String[] advisorNames = this.cachedAdvisorBeanNames;
        if (advisorNames == null) {
            advisorNames = beanFactory.getBeanNamesForType(Advisor.class);
            this.cachedAdvisorBeanNames = advisorNames;
        }
        if (advisorNames.length == 0) {
            return new ArrayList<>();
        }
        List<Advisor> advisors = new ArrayList<>();
        for (String advisorName : advisorNames) {
            advisors.add(beanFactory.getBean(advisorName, Advisor.class));
        }
        return advisors;
    }
}
