package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.exception.BeanDefinitionStoreException;
import github.yuanlin.beans.factory.AutowireCapableBeanFactory;
import github.yuanlin.beans.factory.io.ResourceLoader;

/**
 * BeanDefinitionReader 接口
 *
 * @author yuanlin
 * @date 2022/02/05/20:46
 */
public interface BeanDefinitionReader {

    AutowireCapableBeanFactory getRegistry();

    ResourceLoader getResourceLoader();

    int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

    int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;
}
