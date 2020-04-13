package org.csu.mypetstore.service;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private CatalogService catalogService;

    //根据用户id获取该用户购物车内的所有物品
    public List<CartItem> getCartItemListByUsername(String username)
    {
        List<CartItem> cartItemList =  cartItemMapper.getCartItemListByUsername(username);
        String itemId;
        Item item = new Item();
        for(int i=0;i<cartItemList.size();i++)
        {
            itemId = cartItemList.get(i).getItemId();
            item = catalogService.getItem(itemId);
            cartItemList.get(i).setItem(item);
        }
        return cartItemList;
    }

    //获得用户购物车中某商品的数量（用户购买的数量，不是商品库存）
    public int getQuantity(String username,String itemId)
    {
        return cartItemMapper.getQuantity(username,itemId);
    }

    //向用户的购物车中插入新的商品
    public void insertCartItem(String username, CartItem cartItem)
    {
        cartItemMapper.insertCartItem(username,cartItem);
    }

    //更新用户购物车中某商品的数量
    public void updateCartItemQuantity(String username,String itemId,int quantity)
    {
        cartItemMapper.updateCartItemQuantity(username,itemId,quantity);
    }

    //删除用户购物车中的某商品
    public void removeCartItem(String itemId,String username)
    {
        cartItemMapper.removeCartItem(username,itemId);
    }

    //清空购物车
    public void clear(String username)
    {
        cartItemMapper.clear(username);
    }

    //查看购物车
    public ReturnPack viewCart(String username){
        JSONObject data = new JSONObject();
        List<CartItem> list = getCartItemListByUsername(username);
        String token = JwtUtil.generate(username);
        data.put("token",token);
        data.put("cart",list);
        return ReturnPack.success(data);
    }

//    //将指定物品移出购物车
//    public void removeCartItem(String itemId,String username){
//        removeCartItem(username,itemId);
//    }

    //添加商品进购物车
    public ReturnPack addCartItem(String itemId,String username){
        int stock = catalogService.getInventoryQuantity(itemId);
        if(stock<=0){
            return ReturnPack.fail("库存不足");
        }else{
            JSONObject data = new JSONObject();
//            int q = getQuantity(username,itemId);//方法报错
//            if(q==0){
//                //购物车里没有，添加进购物车
            Item item = itemMapper.getItem(itemId);
            CartItem cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(1);
            cartItem.setInStock(true);
            insertCartItem(username,cartItem);
//            }else {
//                //购物车里有，数量加一
//                q+=1;
//                updateCartItemQuantity(username,itemId,q);
//            }
            List<CartItem> list = getCartItemListByUsername(username);
            String token = JwtUtil.generate(username);
            data.put("token",token);
            data.put("cart",list);
            return ReturnPack.success(data);
        }
    }

    //更新购物车里的物品数量
    public ReturnPack updateCart(String username,String itemId,String quantity){
        JSONObject data = new JSONObject();
        int q = Integer.parseInt(quantity);
        int k = catalogService.getInventoryQuantity(itemId);
        //如果数量超出了库存量，
        if(q>k){
            updateCartItemQuantity(username,itemId,k);
            List<CartItem> list = getCartItemListByUsername(username);
            String token = JwtUtil.generate(username);
            data.put("token",token);
            data.put("cart",list);
            data.put("msg","超出了库存");
            System.out.println();
            return ReturnPack.fail(data);
        }else{
            updateCartItemQuantity(username,itemId,q);
            List<CartItem> list = getCartItemListByUsername(username);
            String token = JwtUtil.generate(username);
            data.put("token",token);
            data.put("cart",list);
            return ReturnPack.success(data);
        }
    }
}
