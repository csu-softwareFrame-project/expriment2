package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.csu.mypetstore.persistence.ItemMapper;
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
    public void removeCartItem(String username,String itemId)
    {
        cartItemMapper.removeCartItem(username,itemId);
    }

    //清空购物车
    public void clear(String username)
    {
        cartItemMapper.clear(username);
    }

    //查看购物车
    public void viewCart(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("loginUser");
        String username = account.getUsername();
        List<CartItem> list = getCartItemListByUsername(username);
//        System.out.println(list.get(0).getItem().getProductId()+" "+list.get(0).getItem().getItemId());
        model.addAttribute("cart",list);
    }

    //将指定物品移出购物车
    public void removeCartItem(String itemId,HttpSession session){
        Account account = (Account)(session.getAttribute("loginUser"));
        removeCartItem(account.getUsername(),itemId);
    }

    public boolean isCartItemExist(String username,String itemId)
    {
        return !(cartItemMapper.isCartItemExist(username,itemId) == null);
    }

    //添加商品进购物车
    public boolean addCartItem(String itemId,HttpSession session,Model model){
        Account account = (Account)(session.getAttribute("loginUser"));
        int stock = catalogService.getInventoryQuantity(itemId);
        if(stock<=0){
            model.addAttribute("msg","库存空了");
            return false;
        }else{
            int q = 0;
            try {
                q = getQuantity(account.getUsername(),itemId);
            }catch (Exception e){

            }
            if(q==0){
                //购物车里没有，添加进购物车
                Item item = itemMapper.getItem(itemId);
                CartItem cartItem = new CartItem();
                cartItem.setItem(item);
                cartItem.setQuantity(1);
                cartItem.setInStock(true);
                insertCartItem(account.getUsername(),cartItem);
            }else {
                //购物车里有，数量加一
                q+=1;
                updateCartItemQuantity(account.getUsername(),itemId,q);
            }

            return true;
        }
    }

    //更新购物车里的物品数量
    public String updateCart(HttpSession session,String itemId,String quantity){
        Account account = (Account)session.getAttribute("loginUser");
        int q = Integer.parseInt(quantity);
        int k = catalogService.getInventoryQuantity(itemId);
        //如果数量超出了库存量，
        if(q>k){
            System.out.println("超出了库存");
            return "库存不足,剩余量:"+k;
        }else{
            updateCartItemQuantity(account.getUsername(),itemId,q);
            return "";
        }
    }
}
