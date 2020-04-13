package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;

import org.csu.mypetstore.service.JwtService;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
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
@RestController
//@RequestMapping("/useraccess")
//@SessionAttributes(value = {"loginUser"})//设置后会使方法中的session参数失效
public class UserAccessController {
    private boolean DEBUG = true;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;

    //进入登录页面
    @RequestMapping("/view_login")
    public String viewLogIn(){
        return "account/signonForm";
    }

    //登录验证跳转
    @PostMapping("/signin")
    public ReturnPack logIn(@RequestBody Map<String, String> params){
        if(DEBUG){
            System.out.println("收到请求");
            System.out.println(params.get("username"));
            System.out.println(params.get("password"));
            System.out.println(params.get("email"));
            System.out.println(params.get("checkCode"));//验证码
        }
        String username = params.get("username");
        String password = params.get("password");
        String checkCode = params.get("checkCode");
        String getterToken = params.get("token");
        JSONObject data = new JSONObject();

        return accountService.signInService(username,password,checkCode,getterToken,data);
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

    @GetMapping("/verifyToken")
    public ReturnPack verifyToken(@RequestParam String token){
        JSONObject data = new JSONObject();
//        if (token != null && !jwtService.isJwtFailure(token)) {
//            if (jwtService.isJwtExpired(token)) {
//                //过期但未失效，重新登录
//                return ReturnPack.fail("用户认证过期，请重新登录");
//            }
//            System.out.println("token内容:"+JwtUtil.decode(token, JwtUtil.SECRET).get("name").asString());
//            return ReturnPack.success(data);
//        }else {
//            return ReturnPack.fail("还没登录");
//        }
        return ReturnPack.success(data);
    }

//    //进入注册页面
//    @GetMapping("/view_sign_up")
//    public String viewSignUp(){ return "account/newAccountForm"; }



    //不知道session为什么无效
//    //登出跳转
//    @GetMapping("/sign_out")
//    public String signOut(HttpSession session){
//        session.removeAttribute("loginUser");
//        return "catalog/main";
//    }


    //登出跳转
//    @GetMapping("/sign_out")
//    public String signOut(HttpSession session){
//        session.removeAttribute("loginUser");
//        return "catalog/main";
//    }

    //测试用
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "just a test";
    }

}
