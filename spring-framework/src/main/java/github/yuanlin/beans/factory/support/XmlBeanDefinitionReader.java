package github.yuanlin.beans.factory.support;

import github.yuanlin.beans.exception.BeanDefinitionStoreException;
import github.yuanlin.beans.factory.FactoryBean;
import github.yuanlin.beans.factory.annotation.Autowired;
import github.yuanlin.beans.factory.annotation.Qualifier;
import github.yuanlin.beans.factory.annotation.Value;
import github.yuanlin.beans.factory.config.*;
import github.yuanlin.beans.factory.io.Resource;
import github.yuanlin.beans.factory.io.ResourceLoader;
import github.yuanlin.context.annotation.Lazy;
import github.yuanlin.context.annotation.Scope;
import github.yuanlin.context.stereotype.Component;
import github.yuanlin.context.stereotype.Repository;
import github.yuanlin.context.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 读取 xml 文件加载 BeanDefinitions
 *
 * @author yuanlin
 * @date 2022/02/05/20:46
 */
@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String FILE_PROTOCOL = "file";

    public static final String JAR_PROTOCOL = "jar";

    public static final String CLASS_SUFFIX = ".class";

    // xml element 标签
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public static final String BEAN_ELEMENT = "bean";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String AOP_ELEMENT = "aop";

    public static final String TX_ELEMENT = "tx";

    // xml attribute, value 标签
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
            throw new BeanDefinitionStoreException("Exception parsing XML document: [" + location + "]", e);
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


    // ------------------------------
    // 解析 xml 文件方法进行配置
    // ------------------------------
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
        BeanDefinition beanDefinition = new GenericBeanDefinition();
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


    // ------------------------------
    // 注解方式进行配置
    // ------------------------------
    protected void parseAnnotation(String basePackage) {
        // 处理多个包名的情况
        String[] basePackages = basePackage.split(",");
        for (int i = 0; i < basePackages.length; i++) {
            basePackages[i] = basePackages[i].trim();
        }
        // 扫描包，获取包下的所有 Class
        Set<Class<?>> classes = new LinkedHashSet<>();
        for (String packagePath: basePackages) {
            Set<Class<?>> subClasses = getClasses(packagePath);
            classes.addAll(subClasses);
        }
        parseAnnotationBeanDefinition(classes);
    }

    private Set<Class<?>> getClasses(String packagePath) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        // github.yuanlin.service -> github/yuanlin/service
        String packageDir = packagePath.replace(".", "/");
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDir);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if (FILE_PROTOCOL.equals(protocol)) {
                    // 对文件路径进行解码: 解决中文乱码问题
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackage(packagePath, filePath, classes);
                } else if (JAR_PROTOCOL.equals(protocol)) {
                    // TODO
                } else {
                    log.error("Unsupported protocol type: [{}], url: [{}]", protocol, url);
                }
            }
        } catch (IOException e) {
            log.error("load resource error: [{}]", packageDir, e);
        }
        return classes;
    }

    private void findAndAddClassesInPackage(String packagePath, String filePath, Set<Class<?>> classes) {
        File file = new File(filePath);
        // 路径不存在直接返回
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        for (File tFile : file.listFiles()) {
            if (tFile.isDirectory()) {
                findAndAddClassesInPackage(packagePath + "." + tFile.getName(),
                        tFile.getAbsolutePath(), classes);
            } else {
                if (tFile.getName().endsWith(CLASS_SUFFIX)) {
                    String className = tFile.getName().
                            substring(0, tFile.getName().length() - CLASS_SUFFIX.length());
                    try {
                        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(packagePath + "." + className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void parseAnnotationBeanDefinition(Set<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (clazz.isInterface()) {
                continue;
            }
            if (isComponent(clazz)) {
                BeanDefinitionHolder beanDefinitionHolder = parseAnnotationBeanDefinition(clazz);
                getRegistry().registerBeanDefinition(beanDefinitionHolder.getBeanName(), beanDefinitionHolder.getBeanDefinition());
            }
        }
    }

    private BeanDefinitionHolder parseAnnotationBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        String beanName = getComponentName(clazz);
        String className = clazz.getName();
        boolean singleton = true;
        boolean lazyInit = false;
        if (clazz.getAnnotation(Lazy.class) != null) {
            lazyInit = clazz.getAnnotation(Lazy.class).value();
        }
        if (clazz.getAnnotation(Scope.class) != null) {
            if (PROTOTYPE_VALUE.equals(clazz.getAnnotation(Scope.class).scopeName())) {
                singleton = false;
            }
        }
        beanDefinition.setBeanClassName(className);
        beanDefinition.setSingleton(singleton);
        beanDefinition.setLazyInit(lazyInit);
        processPropertyAnnotation(clazz, beanDefinition);
        return new BeanDefinitionHolder(beanName, beanDefinition);
    }

    private void processPropertyAnnotation(Class<?> clazz, BeanDefinition beanDefinition) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String name = field.getName();
                if (field.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    String ref = qualifier.value();
                    if ("".equals(ref)) {
                        continue;
                    }
                    RuntimeBeanReference beanReference = new RuntimeBeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
                else {
                    String ref = getSimpleName(field.getType());
                    RuntimeBeanReference beanReference = new RuntimeBeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
            else if (field.isAnnotationPresent(Value.class)) {
                String name = field.getName();
                Value valueAnnotation = field.getAnnotation(Value.class);
                String value = valueAnnotation.value();
                if ("".equals(value)) {
                    continue;
                }
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
            }
        }
    }

    private boolean isComponent(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Component.class) ||
            clazz.isAnnotationPresent(Service.class) ||
            clazz.isAnnotationPresent(Repository.class)) {
            return true;
        }
        return false;
    }

    private String getComponentName(Class<?> clazz) {
        String beanName = "";
        if (clazz.isAnnotationPresent(Component.class)) {
            beanName = clazz.getAnnotation(Component.class).name();
        }
        else if (clazz.isAnnotationPresent(Service.class)) {
            beanName = clazz.getAnnotation(Service.class).name();
        }
        else if (clazz.isAnnotationPresent(Repository.class)) {
            beanName = clazz.getAnnotation(Repository.class).name();
        }
        if ("".equals(beanName)) {
            if (FactoryBean.class.isAssignableFrom(clazz)) {
                beanName = getSimpleName(clazz);
            } else {
                beanName = getSimpleNameForImpl(clazz);
            }
        }
        return beanName;
    }

    private String getSimpleNameForImpl(Class<?> clazz) {
        StringBuilder builder = new StringBuilder(clazz.getInterfaces()[0].getSimpleName());
        // 将首字母转为小写
        // HelloService -> helloService
        builder.setCharAt(0, (char) (builder.charAt(0) + 32));
        return builder.toString();
    }

    private String getSimpleName(Class<?> clazz) {
        StringBuilder builder = new StringBuilder(clazz.getSimpleName());
        // 将首字母转为小写
        // HelloService -> helloService
        builder.setCharAt(0, (char) (builder.charAt(0) + 32));
        return builder.toString();
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
