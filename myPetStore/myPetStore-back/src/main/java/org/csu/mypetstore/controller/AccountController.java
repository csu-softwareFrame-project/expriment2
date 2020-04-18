package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class AccountController {
    private boolean DEBUG = false;
    @Autowired
    AccountService accountService;

    //修改账户信息
    @PutMapping("/accounts")
    public ReturnPack editAccount(HttpServletRequest httpServletRequest, @RequestBody Account account){
        System.out.println(account);
        return accountService.editAccountService(httpServletRequest,account);
    }

    @PutMapping("/passwords")
    public ReturnPack changePassword(HttpServletRequest httpServletRequest,@RequestBody Account account){
        System.out.println(account.getPassword());
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        //加密
//        String newPassword = passwordEncoder.encode(account.getPassword());
//            account.setPassword(newPassword);
        return ReturnPack.success(data);
    }

    //注册功能跳转
    @PostMapping("/accounts/{id}")
    public ReturnPack signUp(@PathVariable("id") String username,@RequestParam Map<String, String> params){
        if (DEBUG){
//            String username = params.get("username");
            String password = params.get("password");
            String firstName = params.get("firstName");
            String lastName = params.get("lastName");
            String email = params.get("email");
            String phone = params.get("phone");
            String address1 = params.get("address1");
            String address2 = params.get("address2");
            String city = params.get("city");
            String state = params.get("state");
            String zip = params.get("zip");
            String country = params.get("country");
            String languagePreference = params.get("languagePreference");
            String favouriteCategoryId = params.get("favouriteCategoryId");
            String listOption = params.get("listOption");
            String bannerOption = params.get("bannerOption");
            System.out.println("1   "+username);
            System.out.println("2   "+password);
            System.out.println("3   "+firstName);
            System.out.println("4   "+lastName);
            System.out.println("5   "+email);
            System.out.println("6   "+phone);
            System.out.println("7   "+address1);
            System.out.println("8   "+address2);
            System.out.println("9   "+city);
            System.out.println("10   "+state);
            System.out.println("11   "+zip);
            System.out.println("12"+country);
            System.out.println("13   "+languagePreference);
            System.out.println("14   "+favouriteCategoryId);
            System.out.println("15   "+listOption);//不是true必是没勾
            System.out.println("16   "+bannerOption);
        }
        return accountService.signUpservice(params);
    }
}
