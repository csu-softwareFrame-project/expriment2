package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemMapper {
    List<CartItem> getCartItemListByUsername(String username);

    void insertCartItem(@Param("username") String username, @Param("cartItem") CartItem cartItem);

    void removeCartItem(@Param("username") String username,@Param("itemId") String itemID);

    void updateCartItemQuantity(@Param("username") String username,@Param("itemId") String itemID, @Param("quantity") int quantity);

    //boolean isCartItemExist(@Param("username") String username,@Param("itemId") String itemId);

    int getQuantity(@Param("username") String username,@Param("itemId") String itemID);

    void clear(String username);
}
