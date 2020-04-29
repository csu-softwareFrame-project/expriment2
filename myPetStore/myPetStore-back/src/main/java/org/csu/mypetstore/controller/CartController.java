package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //todo 显示已下架商品

    //查看购物车
    @GetMapping("/carts")
    public ReturnPack viewCart(HttpServletRequest httpServletRequest, @RequestParam String username){
        JSONObject data = (JSONObject) httpServletRequest.getAttribute("data");
        if(Constants.DEBUG_CONTROLLER) System.out.println("查看购物车");
        return cartService.viewCart(httpServletRequest,username);
    }

    //添加到购物车
    @PostMapping("/carts")
    public ReturnPack addToCart(HttpServletRequest httpServletRequest,@RequestParam String itemId, @RequestParam String username){
        if(Constants.DEBUG_CONTROLLER) System.out.println("添加 itemId： "+itemId+" 进购物车");

        return cartService.addCartItem(httpServletRequest,itemId, username);
    }

    //从购物车中移出
    @DeleteMapping("/carts")
    public ReturnPack removeItemFromCart(HttpServletRequest httpServletRequest,@RequestParam String cartItemId,@RequestParam String username){
        if(Constants.DEBUG_CONTROLLER) System.out.println("移 itemID： "+cartItemId+" 出购物车");
        cartService.removeCartItem(cartItemId,username);
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        List<CartItem> list = cartService.getCartItemListByUsername(username);
        data.put("cart",list);
        return ReturnPack.success(data);
    }

    //更新购物车
    @PutMapping("/carts")
    public ReturnPack updateCart(HttpServletRequest httpServletRequest,String username ,String itemId,String quantity){
        return cartService.updateCart(httpServletRequest,username,itemId,quantity);
    }
}
