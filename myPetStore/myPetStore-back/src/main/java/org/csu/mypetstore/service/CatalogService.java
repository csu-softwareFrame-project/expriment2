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
import org.csu.mypetstore.util.ReturnPack;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;



import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    /**
     * @新增类型
     * @参数：Category对象
     * */
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
        categoryMapper.insertBannerData(category);
    }

    /**
     * @删除类型
     * @参数：类型的id
     */
    public void removeCategory(String categoryId){
        List<Product> productList = productMapper.getProductListByCategory(categoryId);
        List<Item> itemList = new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            itemList = itemMapper.getItemListByProduct(productList.get(i).getProductId());
            for(int j=0;j<itemList.size();j++){
                itemMapper.removeItem(itemList.get(j).getItemId());
                itemMapper.removeQuantity(itemList.get(j).getItemId());
            }
            productMapper.removeProduct(productList.get(i).getProductId());
        }
        categoryMapper.removeCategory(categoryId);
        categoryMapper.removeBannerData(categoryId);
    }

    /**
     * @修改类型信息
     * @参数1：新的Category对象
     * @参数2：修改前的类型id
     */
    public void updateCategory(Category category,String oldId){
        List<Product> productList = productMapper.getProductListByCategory(oldId);
        categoryMapper.updateCategory(category,oldId);
        categoryMapper.updateBannerData(category,oldId);
        for(int i=0;i<productList.size();i++){
            productList.get(i).setCategoryId(category.getCategoryId());
            productMapper.updateProduct(productList.get(i),productList.get(i).getProductId());
        }
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

    /**
     * @新增产品
     * @参数：Product对象
     */
    public void insertProduct(Product product){
        productMapper.insertProduct(product);
    }

    /**
     * @删除产品
     * @参数：产品id
     */
    public void removeProduct(String productId){
        List<Item> itemList = itemMapper.getItemListByProduct(productId);
        for(int i=0;i<itemList.size();i++){
            itemMapper.removeItem(itemList.get(i).getItemId());
            itemMapper.removeQuantity(itemList.get(i).getItemId());
        }
        productMapper.removeProduct(productId);
    }

    /**
     * @修改产品信息
     * @参数：Product对象
     */
    public void updateProduct(Product product,String oldId){
        List<Item> itemList = itemMapper.getItemListByProduct(oldId);
        for(int i=0;i<itemList.size();i++){
            itemList.get(i).setProductId(product.getProductId());
            itemMapper.updateItem(itemList.get(i),itemList.get(i).getItemId());
        }
        productMapper.updateProduct(product,oldId);
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
            if(item == null) return null;
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


    public ReturnPack viewCategoryService(HttpServletRequest httpServletRequest, String categoryId, JSONObject data ){
//        String username =  httpServletRequest.getHeader("UserName");
        //如果有选定宠物类型，展示该类型的菜单；否则展示宠物类型菜单
        if(Constants.REDIS_MODE){
            Jedis jedis = new Jedis("localhost",6379);
            jedis.select(1);
            if(categoryId != ""){
//                System.out.println(jedis.hgetAll("category").size());
                Category category = JSON.parseObject(jedis.hget("category",categoryId),Category.class);
                Map<String,String> productMap = jedis.hgetAll("product");
                List<Product> productList = new LinkedList<>();
                for(String b:productMap.keySet()){
                    Product product = JSON.parseObject(productMap.get(b),Product.class);
//                    System.out.println(product);
                    if(product.getCategoryId().equals(categoryId)) productList.add(product);
                }
                data.put("category", category);
                data.put("productList", productList);
                for(Product product:productList){
                    System.out.println(product);
                }
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

    /**
     * @新增物品
     * @参数：Item对象
     */
    public void insertItem(Item item) {
        itemMapper.insertItem(item);
        itemMapper.insertQuantity(item);
    }

    /**
     * @删除物品
     * @参数：物品id
     */
    public void removeItem(String itemId){
        itemMapper.removeItem(itemId);
        itemMapper.removeQuantity(itemId);
    }

    /**
     * @更新物品
     * @参数：Item对象
     */
    public void updateItem(Item item,String oldId){
        itemMapper.updateItem(item,oldId);
        itemMapper.updateQuantity(item,oldId);
    }

    /**
     * @修改商品状态
     * @参数1：商品id
     * @参数2：商品状态
     * @状态修改为P代表上架，状态修改为S代表下架
     */
    public void updateStatus(String itemId,String status){
        itemMapper.updateStatus(itemId,status);
    }


    /**
     * @获取指定状态的商品
     * @参数：商品状态，P代表上架商品，S代表下架商品
     * @返回商品列表
     */
    public List<Item> getItemListByItemStatus(String status){
        List<Item> itemList = itemMapper.getItemListByItemStatus(status);
        Product product = new Product();
        for(int i=0;i<itemList.size();i++){
            product = productMapper.getProduct(itemList.get(i).getProductId());
            itemList.get(i).setProduct(product);
        }
        return itemList;
    }

    /**
     * @根据关键字搜索品类
     * @参数：品类名的关键字
     * @返回品类列表
     */
    public List<Category> searchCategoryList(String keyword){
        return categoryMapper.searchCategoryList(keyword);
    }

    /**
     * @根据关键字搜索商品
     * @参数：商品名的关键字
     * @返回商品列表
     */
    public List<Item> searchItemList(String keyword){
        List<Item> itemList = itemMapper.searchItemList(keyword);
        Product product = new Product();
        for(int i=0;i<itemList.size();i++){
            itemList.get(i).setProductId(itemList.get(i).getProduct().getProductId());
            product = productMapper.getProduct(itemList.get(i).getProductId());
            itemList.get(i).setProduct(product);
        }
        return itemList;
    }
}
