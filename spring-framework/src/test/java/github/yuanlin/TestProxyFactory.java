package github.yuanlin;

import github.yuanlin.advice.LogArgsAdvice;
import github.yuanlin.advice.LogResultAdvice;
import github.yuanlin.advice.LogThrowsAdvice;
import github.yuanlin.aop.autoproxy.DefaultAdvisorAutoProxyCreator;
import github.yuanlin.aop.pointcut.AspectJExpressionPointcut;
import github.yuanlin.aop.pointcutadvisor.AspectJExpressionPointcutAdvisor;
import github.yuanlin.aop.proxy.ProxyFactory;
import github.yuanlin.aop.targetsource.SingletonTargetSource;
import github.yuanlin.beans.factory.lifecycle.aware.Aware;
import github.yuanlin.beans.factory.lifecycle.aware.BeanFactoryAware;
import github.yuanlin.context.ApplicationContext;
import github.yuanlin.context.support.ClassPathXmlApplicationContext;
import github.yuanlin.model.User;
import github.yuanlin.service.UserService;
import github.yuanlin.service.UserServiceImpl;

/**
 * 测试生成代理对象
 *
 * @author yuanlin
 * @date 2022/02/12/13:14
 */
public class TestProxyFactory {

    public static void main(String[] args) {
//        testCreateProxy();
        testAopUnderSpring();
    }

    public static void testCreateProxy() {
        // bean 实例
        UserService userService = new UserServiceImpl();

        // advice, advisor
        String expression = "execution(* github.yuanlin.service.UserService.*(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(expression);

        String expression1 = "execution(* github.yuanlin.service.UserService.queryException(..))";
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut(expression1);

        AspectJExpressionPointcutAdvisor advisor1 = new AspectJExpressionPointcutAdvisor();
        advisor1.setPointcut(pointcut);
        advisor1.setAdvice(new LogArgsAdvice());

        AspectJExpressionPointcutAdvisor advisor2 = new AspectJExpressionPointcutAdvisor();
        advisor2.setPointcut(pointcut);
        advisor2.setAdvice(new LogResultAdvice());

        AspectJExpressionPointcutAdvisor advisor3 = new AspectJExpressionPointcutAdvisor();
        advisor3.setPointcut(pointcut1);
        advisor3.setAdvice(new LogThrowsAdvice());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisors(advisor1, advisor2, advisor3);
        proxyFactory.addInterface(UserService.class);
        proxyFactory.setTargetSource(new SingletonTargetSource(userService));

        Object proxy = proxyFactory.getProxy();
        UserService service = (UserService) proxy;
        service.createUser("小明", "小", 15);
        service.createUser("小明", "小", 15);
        User user = service.queryUser();
        service.queryException();
    }

    public static void testAopUnderSpring() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        // 测试前置和后置通知
        userService.createUser("小明", "小", 15);
        // 测试异常通知
        userService.queryException();
    }
}
