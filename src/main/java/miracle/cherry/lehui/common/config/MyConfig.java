package miracle.cherry.lehui.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 16:37
 * @Modified:
 * @Description:
 */
@Component// 以组件的方式使用，使用的时候可以直接注入
@ConfigurationProperties(prefix="com.hui.springboot")// 用来指定properties配置文件中的key前缀
@PropertySource("classpath:config.properties")
public class MyConfig {
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
