package org.csu.mypetstore.config;

import org.csu.mypetstore.Interceptor.errorHandlerInterceptor;
import org.csu.mypetstore.Interceptor.orderHandlerInterceptor;
import org.csu.mypetstore.Interceptor.userAccessHandlerInterceptor;
import org.csu.mypetstore.LocaleResolver.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
                //add...添加拦截映射， exclude排除拦截映射
                //SpringBoot已经做好了静态资源映射
                //拦截访问购物车、查看账户信息、添加到购物车请求，没登录则不能访问
                registry.addInterceptor(new userAccessHandlerInterceptor()).addPathPatterns("/cart/view_cart","/main/view_account","/cart/add_item_to_cart").excludePathPatterns("");
                //拦截404
                registry.addInterceptor(new errorHandlerInterceptor()).addPathPatterns("/*","/cart/*","/catalog/*","/main/*","/useraccess/*").excludePathPatterns("/cart/view_cart"
                        ,"/cart/add_item_","/cart/remove_item_from_cart","/catalog/viewCategory","/catalog/viewProduct","/catalog/viewItem"
                        ,"common/error.html","/main/view_main","/main/sign_out","/main/sign_out","/main/search","/main/view_account"
                        ,"/useraccess/view_login","/useraccess/verifyCode","/useraccess/login","/useraccess/view_sign_up","/useraccess/sign_up");
                //拦截访问订单请求
                registry.addInterceptor(new orderHandlerInterceptor()).addPathPatterns("/order/*");
                //                registry.addInterceptor(new errorHandlerInterceptor()).addPathPatterns("/main/viewmaina");
                //super.addInterceptors(registry);
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
