package org.csu.mypetstore.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
import org.csu.mypetstore.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }  //根据类型名称获取类型

    public List<Product> getProductListByCategory(String categoryId){  //根据类型id获取该类型下的所有商品
        return productMapper.getProductListByCategory(categoryId);
    }

    public Product getProduct(String productId){
        if(Constants.REDIS_MODE){
            Jedis jedis = new Jedis("localhost",6379);
            jedis.select(1);
            Product product = JSON.parseObject(jedis.hget("product",productId),Product.class);
            jedis.close();
            return product;
        }
        else return productMapper.getProduct(productId);
    }  //根据商品id获取商品

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }  //获取所有类型

    public List<Product> searchProductList(String keyword) {  //搜索含有关键字的商品
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList = itemMapper.getItemListByProduct(productId);
        for(int i=0;i<itemList.size();i++)
        {
            itemList.get(i).setProductId(itemList.get(i).getProduct().getProductId());
        }
        return itemList;
    }  //根据商品id获取该商品下的所有产品

    public int getInventoryQuantity(String itemId){
        return itemMapper.getInventoryQuantity(itemId);
    }  //获取产品库存

    public Item getItem(String itemId) {
        if(Constants.DEBUG_SERVICE) System.out.println("商品号:"+itemId);
        if(Constants.REDIS_MODE){
            Jedis jedis = new Jedis("localhost",6379);
            jedis.select(1);
            Item item = JSON.parseObject(jedis.hget("item",itemId),Item.class);
            jedis.close();
            return item;
        }else{
            Item item = itemMapper.getItem(itemId);
            item.setProductId(item.getProduct().getProductId());
            return item;
        }
    }  //根据产品id获取产品

    public void updateInventoryQuantity(int increment,String itemId)  //更新产品库存，increment是本次减少的库存数
    {
        itemMapper.updateInventoryQuantity(increment,itemId);
    }

    public List<Item> getItemListByProductId(String productId){
        if(Constants.REDIS_MODE){
            Jedis jedis = new Jedis("localhost",6379);
            jedis.select(1);
            List<Item> itemList = new LinkedList<>();
            Map<String,String> map = jedis.hgetAll("item");
            for(String s:map.keySet()){
                String itemStr = map.get(s);
                Item item = JSON.parseObject(itemStr,Item.class);
                if(item.getProduct().getProductId().equals(productId)) itemList.add(item);
            }
            jedis.close();
            return itemList;
        }
        else return itemMapper.getItemListByProduct(productId);
    }

    public ReturnPack viewCategoryService(HttpServletRequest httpServletRequest,String categoryId, JSONObject data ){
//        String username =  httpServletRequest.getHeader("UserName");
        //如果有选定宠物类型，展示该类型的菜单；否则展示宠物类型菜单
        if(Constants.REDIS_MODE){
            Jedis jedis = new Jedis("localhost",6379);
            jedis.select(1);
           if(categoryId != ""){

               System.out.println(jedis.hgetAll("category").size());
               Category category = JSON.parseObject(jedis.hget("category",categoryId),Category.class);
               Map<String,String> productMap = jedis.hgetAll("product");
               List<Product> productList = new LinkedList<>();
               for(String b:productMap.keySet()){
                   Product product = JSON.parseObject(productMap.get(b),Product.class);
                   System.out.println(product);
                   if(product.getCategoryId().equals(categoryId)) productList.add(product);
               }
               data.put("category", category);
               data.put("productList", productList);
           }else {
               Map<String,String> categoryMap = jedis.hgetAll("category");
               List<Category> categoryList = new LinkedList<>();
               for(String b:categoryMap.keySet()){
                   Category category = JSON.parseObject(categoryMap.get(b),Category.class);
                   categoryList.add(category);
               }
               data.put("categoryList",categoryList);
           }
           jedis.close();
           return ReturnPack.success(data);
        }else{
            if (categoryId != ""){
                Category category = getCategory(categoryId);
                List<Product> productList = getProductListByCategory(categoryId);
                data.put("category", category);
                data.put("productList", productList);
            }else{
                List<Category> categoryList = getCategoryList();
                data.put("categoryList",categoryList);
            }
            return ReturnPack.success(data);
        }
    }

}
