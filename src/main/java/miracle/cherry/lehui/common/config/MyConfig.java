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

    private String url;

    private String manageUrl;
    //登录地址
    private String loginUrl;
    //我的地址
    private String myPageUrl;

    private String projectUrl;

    private String defaultImg;
    private String index;
    private String http;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getManageUrl() {
        return manageUrl;
    }

    public void setManageUrl(String manageUrl) {
        this.manageUrl = manageUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getMyPageUrl() {
        return myPageUrl;
    }

    public void setMyPageUrl(String myPageUrl) {
        this.myPageUrl = myPageUrl;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }
}
