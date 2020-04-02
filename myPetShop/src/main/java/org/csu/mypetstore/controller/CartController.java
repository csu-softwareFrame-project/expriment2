package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @desciption:购物车相关跳转
 * @Date:2020.4.1
 */

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    //查看购物车
    @GetMapping("/view_cart")
    public String viewCart(HttpSession session, Model model){
        cartService.viewCart(session,model);
        return "cart/cart";
    }

    //添加到购物车
    @PostMapping("/add_item_to_cart")
    public String addToCart(String workingItemId){
        return "cart/cart";
    }

    //利用springboot精准匹配的原则处理404
    @RequestMapping("/*")
    public String handle404(){
        return "common/error";
    }
}
