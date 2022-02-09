package github.yuanlin.service;

import github.yuanlin.beans.factory.annotation.Autowired;
import github.yuanlin.context.stereotype.Component;

/**
 * @author yuanlin
 * @date 2022/02/07/20:59
 */
@Component
public class HiServiceImpl implements HiService {

    @Autowired
    private HelloService helloService;

    @Override
    public void hi() {
        System.out.println("hi~");
    }
}
