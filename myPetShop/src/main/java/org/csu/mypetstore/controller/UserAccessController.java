package org.csu.mypetstore.controller;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/useraccess")
@SessionAttributes(value = {"loginUser"})
public class UserAccessController {
    @Autowired
    AccountService accountService;

    @RequestMapping("")
    public String viewLogIn(){
        return "account/signonForm";
    }

    @PostMapping("login")
    public String logIn(String username, String password, Map<String,Object> map, Model model){
        int result = accountService.isPasswordCorrect(username,password);
        switch (result){
            case 0:{
                if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名不存在");
                map.put("msg","用户名不存在");
                return "account/signonForm";
            }
            case 1:{
                if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("用户名或密码错误");
                map.put("msg","用户名或密码错误");
                return "account/signonForm";
            }
            case 2:{
                if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("登录成功");
                Account account = accountService.getAccount(username);
                model.addAttribute("loginUser",account);//登录成功把用户信息放进session
                return "catalog/main";
            } default:{
                if(Constants.DEBUG_MODE&&Constants.DEBUG_CONTROLLER)System.out.println("未知错误");
                map.put("msg","未知错误");
                return "account/signonForm";
            }
        }
    }
}
