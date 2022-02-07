package github.yuanlin.service;

/**
 * @author yuanlin
 * @date 2022/02/07/16:23
 */
public class HelloServiceImpl implements HelloService {

    private String text;

    @Override
    public void hello() {
        System.out.println(text);
    }
}
