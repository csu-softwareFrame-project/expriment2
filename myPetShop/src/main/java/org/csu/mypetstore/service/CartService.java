package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemMapper cartItemMapper;

    public List<CartItem> getCartItemListByUsername(String username)  //根据用户id获取该用户购物车内的所有物品
    {
        return cartItemMapper.getCartItemListByUsername(username);
    }

    public int getQuantity(String username,String itemId)
    {
        return cartItemMapper.getQuantity(username,itemId);
    }  //获得用户购物车中某商品的数量（用户购买的数量，不是商品库存）

    public void insertCartItem(String username, CartItem cartItem)
    {
        cartItemMapper.insertCartItem(username,cartItem);
    }  //向用户的购物车中插入新的商品

    public void updateCartItemQuantity(String username,String itemId,int quantity)
    {
        cartItemMapper.updateCartItemQuantity(username,itemId,quantity);
    }  //更新用户购物车中某商品的数量

    public void removeCartItem(String username,String itemId)
    {
        cartItemMapper.removeCartItem(username,itemId);
    }  //删除用户购物车中的某商品

    public void clear(String username)
    {
        cartItemMapper.clear(username);
    }  //清空购物车
}
