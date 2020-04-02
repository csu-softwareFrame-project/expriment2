package org.csu.mypetstore.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


//主页相关
@Controller
@RequestMapping("/main")
public class MainPageController {

    @GetMapping("/viewmain")
    public String view(){
        return "catalog/main";
    }

    @PostMapping("/search")
    public String search(String keyword){
        return "#";//根据关键字搜索结果并返回
    }

    @GetMapping("/signOut")
    public String signOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "catalog/main";
    }

}
