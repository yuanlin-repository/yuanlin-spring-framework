package github.yuanlin.context.support;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;

/**
 * 可以通过 xml 配置文件来初始化 BeanFactory
 *
 * @author yuanlin
 * @date 2022/02/05/20:56
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        setConfigLocations(configLocations);
        refresh();
    }

    private void setConfigLocations(String[] configLocations) {
        this.configLocations = configLocations;
    }

    @Override
    public AutowireCapableBeanFactory getBeanFactory() throws IllegalStateException {
        return null;
    }

    @Override
    protected void refreshBeanFactory() throws BeansException, IllegalStateException {

    }

}
