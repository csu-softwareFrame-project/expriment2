package org.csu.mypetstore.controller;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


//登录页相关
@Controller
@RequestMapping("/useraccess")
@SessionAttributes(value = {"loginUser"})
public class UserAccessController {
    @Autowired
    AccountService accountService;

    //登录页面映射
    @RequestMapping("")
    public String viewLogIn(){
        return "account/signonForm";
    }

    @GetMapping("/verifyCode")
    public String getVerifyCode(Model model, HttpServletResponse response) throws ServletException, IOException

    {
        VerifyCodeService vc = new VerifyCodeService();
        //获取图片对象
        BufferedImage bi = vc.getImage();
        //获得图片的文本内容
        String text = vc.getText();
        // 将系统生成的文本内容保存到session中
        model.addAttribute("text", text);
        //向浏览器输出图片
        vc.output(bi, response.getOutputStream());
        System.out.println(vc.getText());
        return "";
    }

    //登录验证
    @PostMapping("/login")
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

    //注册页面映射
    @GetMapping("/viewSignup")
    public String viewSignUp(){ return "account/newAccountForm"; }

    @PostMapping("/signUp")
    public String signUp(){
        return "catalog/main";
    }
}
