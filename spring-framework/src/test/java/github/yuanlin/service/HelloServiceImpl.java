package github.yuanlin.service;

import github.yuanlin.beans.factory.annotation.Autowired;
import github.yuanlin.beans.factory.annotation.Qualifier;
import github.yuanlin.beans.factory.annotation.Value;
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

    @Value("hello~")
    private String text;

    @Autowired
    @Qualifier("hiService01")
    private HiService hiService;

    @Override
    public void hello() {
        System.out.println(text);
    }
}
