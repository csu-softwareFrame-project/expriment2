package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartService {

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
    public void removeCartItem(String username,String itemId)
    {
        cartItemMapper.removeCartItem(username,itemId);
    }

    //清空购物车
    public void clear(String username)
    {
        cartItemMapper.clear(username);
    }

    public void viewCart(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("loginUser");
        String username = account.getUsername();
        List<CartItem> list = getCartItemListByUsername(username);
        model.addAttribute("cart",list);
    }
}
