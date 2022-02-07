package github.yuanlin.service;

import github.yuanlin.context.annotation.Lazy;
import github.yuanlin.context.annotation.Scope;
import github.yuanlin.context.stereotype.Component;

/**
 * @author yuanlin
 * @date 2022/02/07/16:23
 */
@Lazy
@Scope(scopeName = "singleton")
@Component
public class HelloServiceImpl implements HelloService {

    private String text;

    @Override
    public void hello() {
        System.out.println(text);
    }
}
