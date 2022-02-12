package github.yuanlin;

import github.yuanlin.context.ApplicationContext;
import github.yuanlin.context.support.ClassPathXmlApplicationContext;
import github.yuanlin.service.UserService;

/**
 * 测试主类
 *
 * @author yuanlin
 * @date 2022/02/05/20:12
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        // 测试前置和后置通知
        userService.createUser("小明", "小", 15);
        userService.queryUser();
    }
}
