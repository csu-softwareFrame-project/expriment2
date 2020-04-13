package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @desciption:购物车相关跳转
 * @Date:2020.4.1
 */

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    //查看购物车
    @GetMapping("/viewCart")
    public ReturnPack viewCart(@RequestParam String username){
        if(Constants.DEBUG_CONTROLLER) System.out.println("查看购物车");
        return cartService.viewCart(username);
    }

    //添加到购物车
//    @PostMapping("/addToCart")
//    public ReturnPack addToCart(@RequestBody Map<String,String> params){
//        String itemID = params.get("id");
//        String username = params.get("username");
//        if(Constants.DEBUG_CONTROLLER) System.out.println("添加 itemId： "+itemID+" 进购物车");
//        return cartService.addCartItem(itemID, username);
//    }

    //添加到购物车
    @PostMapping("/addToCart")
    public ReturnPack addToCart(@RequestParam String itemId, @RequestParam String username){
        if(Constants.DEBUG_CONTROLLER) System.out.println("添加 itemId： "+itemId+" 进购物车");
        return cartService.addCartItem(itemId, username);
    }

    //从购物车中移出
    @PostMapping("/removeFromCart")
    public ReturnPack removeItemFromCart(@RequestParam String cartItemId,@RequestParam String username){
        if(Constants.DEBUG_CONTROLLER) System.out.println("移 itemID： "+cartItemId+" 出购物车");
        cartService.removeCartItem(cartItemId,username);
        JSONObject data = new JSONObject();
        List<CartItem> list = cartService.getCartItemListByUsername(username);
        data.put("cart",list);
        return ReturnPack.success(data);
//        return cartService.viewCart(username);
    }

    //更新购物车
    @PostMapping("/updateCart")
    public ReturnPack updateCart(String username ,String itemId,String quantity){
        return cartService.updateCart(username,itemId,quantity);
    }
}
