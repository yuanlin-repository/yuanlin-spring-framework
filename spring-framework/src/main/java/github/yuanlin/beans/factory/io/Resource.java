package github.yuanlin.beans.factory.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 获取资源（配置文件）的接口
 *
 * @author yuanlin
 * @date 2022/02/06/20:33
 */
public interface Resource {

    /**
     * 通过输入流的方式获取资源
     * @return 待获取资源的输入流
     * @throws Exception IO异常
     */
    InputStream getInputStream() throws IOException;
}
