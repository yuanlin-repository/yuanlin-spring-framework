package github.yuanlin;

import github.yuanlin.context.ApplicationContext;
import github.yuanlin.context.support.ClassPathXmlApplicationContext;
import github.yuanlin.model.Student;
import github.yuanlin.service.HelloService;
import github.yuanlin.service.HiService;
import github.yuanlin.service.TestFactoryBean;

/**
 * 测试 ClassPathXmlApplicationContext 功能
 *
 * @author yuanlin
 * @date 2022/02/09/14:54
 */
public class TestClassPathXmlApplicationContext {

    public static void main(String[] args) throws Exception {
//        testGetBeanConfigureThroughXmlFile();
//        testGetBeanConfigureThroughAnnotation();
//        testFactoryBeanConfigureThroughAnnotation();
//        testFactoryBeanConfigureThroughXmlFile();
        testCircularReference();
    }

    public static void testGetBeanConfigureThroughXmlFile() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloService helloService = (HelloService) applicationContext.getBean("helloService");
        helloService.hello();
        HiService hiService = (HiService) applicationContext.getBean("hiService");
    }

    public static void testGetBeanConfigureThroughAnnotation() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-annotation.xml");
        HelloService helloService = (HelloService) applicationContext.getBean("helloService");
        helloService.hello();
        System.out.println("test finish..");
    }

    public static void testFactoryBeanConfigureThroughAnnotation() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-annotation.xml");
        Object student = applicationContext.getBean("testFactoryBean");
        System.out.println(student);
        Object testFactoryBean = applicationContext.getBean("&testFactoryBean");
        Student student1 = ((TestFactoryBean) testFactoryBean).getObject();
        System.out.println(student1);
        System.out.println("test finish..");
    }

    public static void testFactoryBeanConfigureThroughXmlFile() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Object student = applicationContext.getBean("testFactoryBean");
        System.out.println(student);
        Object testFactoryBean = applicationContext.getBean("&testFactoryBean");
        Student student1 = ((TestFactoryBean) testFactoryBean).getObject();
        System.out.println(student1);
        System.out.println("test finish..");
    }

    public static void testCircularReference() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloService helloService = (HelloService) applicationContext.getBean("helloService");
        helloService.hello();
        HiService hiService = (HiService) applicationContext.getBean("hiService");
        System.out.println("test finish..");
    }
}
