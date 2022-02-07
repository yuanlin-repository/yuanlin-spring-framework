package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.io.ResourceLoader;

/**
 * BeanDefinitionReader 的抽象类
 *
 * @author yuanlin
 * @date 2022/02/05/20:46
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    /**
     * 用于注册读取的 BeanDefinition
     */
    protected AutowireCapableBeanFactory beanFactory;

    /**
     * 资源加载器
     */
    protected ResourceLoader resourceLoader;

    @Override
    public AutowireCapableBeanFactory getRegistry() {
        return beanFactory;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
