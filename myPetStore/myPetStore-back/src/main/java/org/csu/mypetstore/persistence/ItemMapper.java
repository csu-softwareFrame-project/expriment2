package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {
    void updateInventoryQuantity(@Param("increment") int increment, @Param("itemId") String itemId);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    List<Item> getItemListByItemStatus(String status);

    List<Item> searchItemList(String keyword);

    Item getItem(String itemId);

    void insertItem(Item item);

    void insertQuantity(Item item);

    void removeItem(String itemId);

    void removeQuantity(String itemId);

    void updateItem(@Param("item") Item item,@Param("oldId") String oldId);

    void updateQuantity(@Param("item") Item item,@Param("oldId") String oldId);

    void updateStatus(@Param("itemId") String itemId,@Param("status") String status);
}
