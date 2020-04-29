package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.service.MailService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/getVerify")
    public ReturnPack getVerify(HttpServletRequest httpServletRequest, @RequestParam String email){
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
        }
        String verify = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的验证码为："+verify;
        try {
            mailService.sendSimpleMail(email, "验证码", message);
            data.put("verifyCode", verify);
        }catch (Exception e){
            e.printStackTrace();
            return ReturnPack.fail("发送失败，推测是邮箱问题");
        }
        return ReturnPack.success(data);
    }

    @RequestMapping("/getCheckCode")
    @ResponseBody
    public String getCheckCode(String email, Model model, HttpSession session){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        session.setAttribute("checkCode", checkCode);
        String message = "您的登陆验证码为："+checkCode;
        model.addAttribute("checkCode", checkCode);
        try {
            mailService.sendSimpleMail(email, "登陆验证码", message);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return checkCode;
    }
}
