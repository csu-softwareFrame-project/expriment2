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

import java.util.ArrayList;
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
    }

    /**
     * @修改类型信息
     * @参数：Category对象
     */
    public void updateCategory(Category category,String oldId){
        List<Product> productList = productMapper.getProductListByCategory(oldId);
        categoryMapper.updateCategory(category,oldId);
        for(int i=0;i<productList.size();i++){
            productList.get(i).setCategoryId(category.getCategoryId());
            productMapper.updateProduct(productList.get(i),productList.get(i).getProductId());
        }
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
     *
     * @更新物品
     * @参数：Item对象
     */
    public void updateItem(Item item,String oldId){
        itemMapper.updateItem(item,oldId);
        itemMapper.updateQuantity(item,oldId);
    }
}
