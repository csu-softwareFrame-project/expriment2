package org.csu.mypetstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class ManagementController {

    //跳转到用户管理界面
    @RequestMapping("/accountManagement")
    public String manageAccount(){
        return "management/accountManagement";
    }

    //跳转到品类管理界面
    @RequestMapping("/categoryManagement")
    public String manageCategory(){
        return "management/categoryManagement";
    }

    //跳转到产品管理界面
    @RequestMapping("/productManagement")
    public String manageProduct(){
        return "management/productManagement";
    }

    //跳转到商品管理界面
    @RequestMapping("/itemManagement")
    public String manageItem(){
        return "management/itemManagement";
    }

    //跳转到订单管理界面
    @RequestMapping("/orderManagement")
    public String manageOrder(){
        return "management/orderManagement";
    }
}
