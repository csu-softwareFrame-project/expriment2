package org.csu.mypetstore.controller;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
 * @desciption:用户相关跳转
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
    public String logIn(String username, String password, String email, String checkCode, Map<String,Object> map, Model model, HttpSession session){
        System.out.println(checkCode);//从用户处获取验证码
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        if(accountService.userAccessService(username, password,checkCode,session,map, model)){
            session.removeAttribute("checkCode");
            System.out.println("登录成功");
            return "redirect:/main/view_main";
        }else {
            session.removeAttribute("checkCode");
            System.out.println("登录失败");
            return "account/signonForm";
        }
    }

    @RequestMapping(value ="/alogin", method = RequestMethod.GET)
    @ResponseBody
    public String alogin(String username){
        Account account = accountService.getAccount(username);
        if(account != null && !username.equals("")){
            return  "Exist";
        }
        else if (username.equals("")){
            return "Null";
        }
        else {
            return "NotExist";
        }
    }

    //进入注册页面
    @GetMapping("/view_sign_up")
    public String viewSignUp(){ return "account/newAccountForm"; }

    //注册功能跳转
    @PostMapping("/sign_up")//一个小坑：用类获取参数的时候，类的属性名要和前端的name一样
    public String signUp( Account account, String repeatedPassword, Map<String,Object> map){
        System.out.println(account.getAddress2()+" "+account.getEmail());
        if(accountService.signUpservice(account,repeatedPassword,map)) {
            return "account/signOnForm";
        } else{
            return "account/newAccountForm";
        }
    }

    //不知道session为什么无效
//    //登出跳转
//    @GetMapping("/sign_out")
//    public String signOut(HttpSession session){
//        session.removeAttribute("loginUser");
//        return "catalog/main";
//    }




}
