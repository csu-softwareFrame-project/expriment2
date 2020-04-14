package org.csu.mypetstore.Interceptor;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
        String username =  httpServletRequest.getHeader("UserName");
        // 如果不是映射到方法直接通过
//        if (!(object instanceof HandlerMethod)) {
//            return true;
//        }
        System.out.println("methods:"+httpServletRequest.getMethod());
        //option方法，放行
//        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
//            System.out.println("OPTIONS请求，放行");
//            return true;
//        }

        //打印请求头
//        int i = 0;
//        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            i++;
//            String element = headerNames.nextElement();
//            System.out.println(element+":"+httpServletRequest.getHeader(element));
//            if(i>100) break;
//        }
        //如果没有token，就需要颁发
        if(token.equals("undefined")) {
           httpServletResponse.sendError(401);
        };
        //token过期
        if(JwtUtil.isExpirate(token)){
            System.out.println("token过期，拦截");
            httpServletResponse.sendError(401);
        }
        //token无效
        if(!JwtUtil.dec(token).equals(username)){
            System.out.println("token无效");
            //抛出401错误
            httpServletResponse.sendError(401);
        }

        System.out.println("进入的token:"+token+".进入的username:"+username);
        System.out.println("token没过期，放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
