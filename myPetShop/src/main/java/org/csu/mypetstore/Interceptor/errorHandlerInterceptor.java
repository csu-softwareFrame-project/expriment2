package org.csu.mypetstore.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class errorHandlerInterceptor implements HandlerInterceptor {

    //todo:仍然存在一次请求被多次拦截
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
//        System.out.println("进入拦截器");
        if(response != null && modelAndView !=null){
            if(response.getStatus() == 404) modelAndView.setViewName("common/error.html");
            if(response.getStatus() == 500) modelAndView.setViewName("common/error.html");
        }
    }
}
