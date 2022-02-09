package github.yuanlin.service;

import github.yuanlin.context.stereotype.Component;

/**
 * @author yuanlin
 * @date 2022/02/07/20:59
 */
@Component
public class HiServiceImpl implements HiService {

    @Override
    public void hi() {
        System.out.println("hi~");
    }
}
