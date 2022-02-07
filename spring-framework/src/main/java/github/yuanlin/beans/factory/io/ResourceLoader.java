package github.yuanlin.beans.factory.io;

import java.io.InputStream;
import java.net.URL;

/**
 * 资源加载器（加载指定路径下的资源）
 *
 * @author yuanlin
 * @date 2022/02/06/20:34
 */
public class ResourceLoader {

    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
