package github.yuanlin;

import github.yuanlin.beans.factory.support.DefaultListableBeanFactory;
import github.yuanlin.beans.factory.support.XmlBeanDefinitionReader;

/**
 * 测试 XmlBeanDefinitionReader
 *
 * @author yuanlin
 * @date 2022/02/07/16:25
 */
public class TestXmlBeanDefinitionReader {

    public static void main(String[] args) {
        testLoadXmlFile();
    }

    /**
     * 测试加载 xml 文件
     */
    public static void testLoadXmlFile() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("spring.xml");
    }
}
