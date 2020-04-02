package org.csu.mypetstore.controller;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


/**
 * @desciption:登录注册相关跳转
 * @Date:2020.4.1
 */
@Controller
@RequestMapping("/useraccess")
@SessionAttributes(value = {"loginUser"})
public class UserAccessController {
    @Autowired
    AccountService accountService;
    //进入登录页面
    @RequestMapping("/view_login")
    public String viewLogIn(){
        return "account/signonForm";
    }

    //登录验证跳转
    @PostMapping("/login")
    public String logIn(String username, String password, String checkCode, Map<String,Object> map, Model model, HttpSession session){
        System.out.println(checkCode);//从用户处获取验证码
        System.out.println(session.getAttribute("checkCode"));//从服务器获取正确验证码
        if(accountService.userAccessService(username, password, map, model)){
            System.out.println("登录成功");
            return "catalog/main";
        }else {
            System.out.println("登录失败");
            return "account/signonForm";
        }
    }


    //进入注册页面
    @GetMapping("/view_sign_up")
    public String viewSignUp(){ return "account/newAccountForm"; }

    //注册功能跳转
    @PostMapping("/sign_up")
    public String signUp(Account account,String repeatedPassword, Map<String,Object> map){
        if(accountService.signUpservice(account,repeatedPassword,map)) {
            return "account/signOnForm";
        } else{
            return "account/newAccountForm";
        }
    }

    //不知道为什么无效
//    //登出跳转
//    @GetMapping("/sign_out")
//    public String signOut(HttpSession session){
//        session.removeAttribute("loginUser");
//        return "catalog/main";
//    }


    //利用springboot精准匹配的原则处理404
    @RequestMapping("*")
    public String handle404() {
        return "common/error";
    }

}
