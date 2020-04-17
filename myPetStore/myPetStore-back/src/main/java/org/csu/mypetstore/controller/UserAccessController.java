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
public class UserAccessController {
    private boolean DEBUG = true;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;

    //登录验证跳转
    @PostMapping("/users")
    public ReturnPack signIn(@RequestBody Map<String, String> params){
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
        return accountService.signInService(username,password,checkCode,getterToken);
    }

    @RequestMapping(value ="/alogin", method = RequestMethod.GET)
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

    @PostMapping("/tokens")
    public ReturnPack verifyToken(HttpServletRequest httpServletRequest,@RequestParam String token){
        JSONObject data = new JSONObject();
//        String username =  httpServletRequest.getHeader("UserName");
//        data.put("token",JwtUtil.generate(username));
        return ReturnPack.success(data);
    }


    //测试用
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "just a test";
    }

}
