package miracle.cherry.lehui.common.config;

import miracle.cherry.lehui.common.Filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.servlet.Filter;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * @Description:乐汇项目后台
 * @Copyright: MengHui
 * @Author: MengHui
 * @Date: 2019-07-15 11
 * @Modified:
 * @Description:
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Resource
    MyConfig myConfig;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("miracle.cherry.lehui"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("乐汇项目后台 RESTful APIs")
                .description("乐汇项目api接口文档")
                .version("1.0")
                .build();
    }




    @Bean
    public Filter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean uploadFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("loginFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("loginFilter");
        registration.setOrder(1);
        return registration;
    }



}
