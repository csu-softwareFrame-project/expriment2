package org.csu.mypetstore.Interceptor;

import org.csu.mypetstore.domain.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:购物车拦截器
 * @date:2020.4.1
 */
@Component
public class userAccessHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) request.getSession().getAttribute("loginUser");
        if(account == null){//没有登录，返回登录界面
            request.setAttribute("msg","请先登录");
            request.getRequestDispatcher("/useraccess/view_login").forward(request,response);
            return false;
        }else{
            //已登录，放行
            return true;
        }
    }
}
