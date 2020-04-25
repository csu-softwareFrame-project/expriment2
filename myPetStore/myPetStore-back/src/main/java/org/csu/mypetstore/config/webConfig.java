package org.csu.mypetstore.config;

import org.csu.mypetstore.Interceptor.AuthenticationInterceptor;
import org.csu.mypetstore.LocaleResolver.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description:注册拦截器
 * @Date:2020.4.1
 */

@Configuration
public class webConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Bean//将组件注册到容器
    public WebMvcConfigurerAdapter webMvcAutoConfigurationAdapter() {

        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //修改映射
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry){
                //拦截所有数据请求，检查token有效性
                registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/users","/categories","/products","/items","/results","/newTokens"
                                ,"/mail/getCheckCode","/management/*","/accounts/*","/response");
            }
        };
        return adapter;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        //Swagger ui Mapping
//        registry.addResourceHandler("*")
//                .addResourceLocations("classpath:/resources/");
//
//    }
}
