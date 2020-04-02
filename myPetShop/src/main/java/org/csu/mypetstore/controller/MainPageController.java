package org.csu.mypetstore.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @desciption:主页相关跳转
 * @Date:2020.4.1
 */
@Controller
@RequestMapping("/main")
public class MainPageController {

    //进入主页
    @GetMapping("/viewmain")
    public String viewMain(){
        return "catalog/main";
    }


    //搜索功能
    @PostMapping("/search")
    public String search(String keyword){
        return "#";//根据关键字搜索结果并返回
    }

    //登出跳转
    @GetMapping("/signOut")
    public String signOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "catalog/main";
    }

    //进入账户信息
    @GetMapping("/view_account")
    public String viewAccount(){
        return "account/editAccountForm";
    }

    //利用springboot精准匹配的原则处理404
    @RequestMapping("/*")
    public String handle404(){
        return "common/error";
    }
}
