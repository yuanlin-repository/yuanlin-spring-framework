package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.exception.BeanDefinitionStoreException;
import github.yuanlin.beans.factory.config.*;
import github.yuanlin.beans.factory.io.Resource;
import github.yuanlin.beans.factory.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 读取 xml 文件加载 BeanDefinitions
 *
 * @author yuanlin
 * @date 2022/02/05/20:46
 */
@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    // xml element 标签
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public static final String BEAN_ELEMENT = "bean";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String AOP_ELEMENT = "aop";

    public static final String TX_ELEMENT = "tx";

    // xml attribute value 标签
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    // <bean/>
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String SINGLETON_VALUE = "singleton";

    public static final String PROTOTYPE_VALUE = "prototype";

    public static final String LAZY_INIT_ATTRIBUTE = "lazy-init";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String TRUE_VALUE = "true";

    public static final String FALSE_VALUE = "false";

    // <aop/>


    // <tx/>

    public XmlBeanDefinitionReader(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.resourceLoader = new ResourceLoader();
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        int count = 0;
        for (String location : locations) {
            count += loadBeanDefinitions(location);
        }
        return count;
    }

    // TODO 读取文件加载注册 BeanDefinition
    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        log.info("loading the file under: [{}]", location);
        // 获取 location 对应的文件
        Resource resource = getResourceLoader().getResource(location);
        // 获取文件的输入流
        int count = 0;
        try (InputStream inputStream = resource.getInputStream()) {
            count = doLoadBeanDefinitions(inputStream);
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Exception parsing XML document: [" + location + "]");
        }
        return count;
    }

    protected int doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        // 将 xml 文件解析为 dom 树
        Document document = documentBuilder.parse(inputStream);
        int countBefore = getRegistry().getBeanDefinitionCount();
        registerBeanDefinitions(document);
        return getRegistry().getBeanDefinitionCount() - countBefore;
    }

    public void registerBeanDefinitions(Document document) {
        Element rootElement = document.getDocumentElement();
        parseBeanDefinitions(rootElement);
    }

    protected void parseBeanDefinitions(Element rootElement) {
        NodeList childNodes = rootElement.getChildNodes();
        String basePackage = null;
        // 检查是否通过注解配置
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (element.getTagName().equals(COMPONENT_SCAN_ELEMENT)) {
                    basePackage = element.getAttribute(BASE_PACKAGE_ATTRIBUTE);
                    break;
                }
            }
        }
        if (basePackage != null) {
            // 通过注解配置
            parseAnnotation(basePackage);
        } else {
            // 通过 xml 文件配置
            parseElement(rootElement);
        }
    }

    protected void parseElement(Element rootElement) {
        NodeList childNodes = rootElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (element.getTagName().equals(BEAN_ELEMENT)) {
                    // 解析 <bean> </bean>
                    processBeanDefinition(element);
                } else if (element.getTagName().equals(AOP_ELEMENT)) {
                    // 解析 <aop> </aop>
                    processAopElement(element);
                } else if (element.getTagName().equals(TX_ELEMENT)) {
                    // 解析 <tx> </tx>
                    processTransactionElement(element);
                }
            }
        }
    }

    /**
     * 解析指定包下的注解配置
     */
    protected void parseAnnotation(String basePackage) {

    }

    private void processBeanDefinition(Element element) {
        BeanDefinitionHolder beanDefinitionHolder = parseBeanDefinitionElement(element);
        getRegistry().registerBeanDefinition(beanDefinitionHolder.getBeanName(), beanDefinitionHolder.getBeanDefinition());
    }

    /**
     * 解析 bean 标签
     */
    private BeanDefinitionHolder parseBeanDefinitionElement(Element element) {
        // beanName
        if (!element.hasAttribute(ID_ATTRIBUTE)) {
            log.error("Tag 'bean' must have a 'id' attribute: [{}]", element);
        }
        String id = element.getAttribute(ID_ATTRIBUTE);
        // 全限定类名
        if (!element.hasAttribute(CLASS_ATTRIBUTE)) {
            log.error("Tag 'bean' must have a 'class' attribute: [{}]", element);
        }
        String className = element.getAttribute(CLASS_ATTRIBUTE);
        // 是否单例
        boolean singleton = true;
        if (element.hasAttribute(SCOPE_ATTRIBUTE)) {
            String scope = element.getAttribute(SCOPE_ATTRIBUTE);
            if (scope.equals(PROTOTYPE_VALUE)) {
                singleton = false;
            }
        }
        // 是否懒加载
        boolean lazyInit = false;
        if (element.hasAttribute(LAZY_INIT_ATTRIBUTE)) {
            String isLazyInit = element.getAttribute(LAZY_INIT_ATTRIBUTE);
            if (isLazyInit.equals(TRUE_VALUE)) {
                lazyInit = true;
            }
        }
        BeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(className);
        beanDefinition.setSingleton(singleton);
        beanDefinition.setLazyInit(lazyInit);
        // 处理属性注入
        // <bean>
        //      <property name="xxx" ref(value)="xxx"> </property>
        // </bean>
        processPropertyElement(element, beanDefinition);
        return new BeanDefinitionHolder(id, beanDefinition);
    }

    /**
     * 解析 property 标签
     */
    private void processPropertyElement(Element beanElement, BeanDefinition beanDefinition) {
        NodeList childNodes = beanElement.getElementsByTagName(PROPERTY_ELEMENT);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (!element.hasAttribute(NAME_ATTRIBUTE)) {
                    log.error("Tag 'property' must have a 'name' attribute: [{}]", element);
                }
                String name = element.getAttribute(NAME_ATTRIBUTE);
                boolean hasValueAttribute = element.hasAttribute(VALUE_ATTRIBUTE);
                boolean hasRefAttribute = element.hasAttribute(REF_ATTRIBUTE);
                if (hasValueAttribute && hasRefAttribute) {
                    log.error("Tag 'property' is only allowed to contain either 'value' attribute OR 'ref' attribute: [{}]", element);
                } else if (hasValueAttribute) {
                    // value 属性
                    String value = element.getAttribute(VALUE_ATTRIBUTE);
                    // 在 beanDefinition 中保存要注入的属性和值
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else if (hasRefAttribute) {
                    // ref 属性
                    String ref = element.getAttribute(REF_ATTRIBUTE);
                    RuntimeBeanReference beanReference = new RuntimeBeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                } else {
                    log.error("Tag 'property' must have a 'value' or 'ref' attribute: [{}]", element);
                }
            }
        }
    }

    /**
     * 解析 aop 标签
     */
    private void processAopElement(Element element) {
        // TODO AOP 功能
    }

    /**
     * 解析 tx 标签
     */
    private void processTransactionElement(Element element) {
        // TODO TX 功能
    }
}
