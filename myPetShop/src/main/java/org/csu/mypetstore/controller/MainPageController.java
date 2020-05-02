package org.csu.mypetstore.controller;

import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @desciption:主页相关跳转
 * @Date:2020.4.1
 */
@Controller
@RequestMapping("/main")
public class MainPageController {

    @Autowired
    CatalogService catalogService;

    //进入主页
    @GetMapping("/view_main")
    public String viewMain(){
        return "redirect:/main/view_managementPage";
    }

    //进入管理主页
    @GetMapping("/view_managementPage")
    public String viewManagementPage(){
        return "management/mainPage";
    }

    //登出跳转
    @GetMapping("/sign_out")
    public String signOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "catalog/main";
    }

    //搜索功能
    @PostMapping("/search")
    public String search(String keyword, Map<String,Object> map){
        map.put("productList",catalogService.searchProductList(keyword));
        return "catalog/search";//根据关键字搜索结果并返回
    }

    //进入账户信息
    @GetMapping("/view_account")
    public String viewAccount(){
        return "account/editAccountForm";
    }

}
