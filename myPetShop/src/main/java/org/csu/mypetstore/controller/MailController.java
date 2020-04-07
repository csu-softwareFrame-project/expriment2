package org.csu.mypetstore.controller;

import org.csu.mypetstore.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

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
