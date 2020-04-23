package org.csu.mypetstore.hook;

import com.alibaba.fastjson.JSON;
import com.google.gson.annotations.JsonAdapter;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.csu.mypetstore.util.SerializeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:高益基
 * @Description:creat钩子函数，清空redisDB。
 */
@Configuration
public class backInitializer {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ItemMapper itemMapper;
    @PostConstruct
    public void postConstruct(){
//        System.out.println("system started, triggered by postConstruct.");
        //开启后端服务器前把信息输入redis
        if(Constants.REDIS_MODE){
            List<Category> categoryList = categoryMapper.getCategoryList();
            productMapper.getProductListByCategory("categoryID");
            itemMapper.getItemListByProduct("itemID");
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.select(1);
            jedis.set("k1","v1");
            Map<String,String> categoryMap = new HashMap<>();
            Map<String,String> productMap = new HashMap<>();
            Map<String,String> itemMap = new HashMap<>();
            for(Category category:categoryList){

                categoryMap.put(category.getCategoryId(), JSON.toJSONString(category));
                List<Product> productList = productMapper.getProductListByCategory(category.getCategoryId());
                for(Product product: productList){
                    productMap.put(product.getProductId(),JSON.toJSONString(product));
                    List<Item> itemList = itemMapper.getItemListByProduct(product.getProductId());
                    for(Item item: itemList){
                        itemMap.put(item.getItemId(), JSON.toJSONString(item));
                    }
                }
            }
            jedis.hmset("category",categoryMap);
            jedis.hmset("product",productMap);
            jedis.hmset("item",itemMap);
            System.out.println("目录信息输入redis");
            jedis.close();
        }
    }
}
