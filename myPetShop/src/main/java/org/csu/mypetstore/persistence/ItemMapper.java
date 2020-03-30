package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ItemMapper {
    List<Item> getItemListByProduct(String productId);//使用商品id获取产品列表

    Item getItem(String itemId);//获取产品
}
