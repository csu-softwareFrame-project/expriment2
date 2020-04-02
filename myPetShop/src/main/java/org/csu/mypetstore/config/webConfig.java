package org.csu.mypetstore.config;

import org.csu.mypetstore.Interceptor.userAccessHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description:注册拦截器
 * @Date:2020.4.1
 */

@Configuration
public class webConfig extends WebMvcConfigurerAdapter {
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
                //拦截访问购物车、查看账户信息请求，没登录则不能访问
                registry.addInterceptor(new userAccessHandlerInterceptor()).addPathPatterns("/cart/view_cart","/main/view_account").excludePathPatterns("");
//                registry.addInterceptor(new errorHandlerInterceptor()).addPathPatterns("/main/viewmaina");
                //super.addInterceptors(registry);
            }
        };
        return adapter;
    }
}
