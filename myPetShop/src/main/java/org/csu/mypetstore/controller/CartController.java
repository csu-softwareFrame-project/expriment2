package org.csu.mypetstore.controller;

import org.csu.mypetstore.Constants;
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
        if(Constants.DEBUG_CONTROLLER) System.out.println("查看购物车");
        cartService.viewCart(session,model);
        return "cart/cart";
    }

    //添加到购物车
    @GetMapping("/add_item_to_cart")
    public String addToCart(String itemId,String productId,HttpSession session,Model model){
        if(Constants.DEBUG_CONTROLLER) System.out.println("添加 itemID： "+itemId+" 进购物车");
        if(cartService.addCartItem(itemId,session,model)) return "forward:/cart/view_cart";
        else return "viewProduct?productId="+productId;
    }

    //从购物车中移出
    @GetMapping("/remove_item_from_cart")
    public String removeItemFromCart(String cartItemId,HttpSession session,Model model){
        if(Constants.DEBUG_CONTROLLER) System.out.println("移 itemID： "+cartItemId+" 出购物车");
        cartService.removeCartItem(cartItemId,session);
        cartService.viewCart(session,model);
        return "cart/cart";
    }

}
