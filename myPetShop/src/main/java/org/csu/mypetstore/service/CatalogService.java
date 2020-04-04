package org.csu.mypetstore.service;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
        return productMapper.getProduct(productId);
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
        Item item = itemMapper.getItem(itemId);
        item.setProductId(item.getProduct().getProductId());

        return item;
    }  //根据产品id获取产品

    public void updateInventoryQuantity(int increment,String itemId)  //更新产品库存，increment是本次减少的库存数
    {
        itemMapper.updateInventoryQuantity(increment,itemId);
    }

    public List<Item> getItemListByProductId(String productId){
        return itemMapper.getItemListByProduct(productId);
    }

    public boolean viewCategoryService(String categoryId, Model model){
        //如果有选定宠物类型，展示该类型的菜单；否则展示宠物类型菜单
        if (categoryId != null){
            Category category = getCategory(categoryId);
            List<Product> productList = getProductListByCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("productList", productList);
            return true;
        }else{
            List<Category> categoryList = getCategoryList();
            model.addAttribute("categoryList",categoryList);
            return false;
        }
    }
}
