package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.domain.receiveBean.receiveItem;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RestController
public class ManageCatalogController {
    private static final boolean DEBUG = true;

    @Autowired
    private CatalogService catalogService;

    //删除种类
    @DeleteMapping("/management/categories")
    public ReturnPack deleteCategories(@RequestBody List<String> categoryIdList){
        List<String> ORIGIN = new LinkedList<>();
        ORIGIN.add("FISH");ORIGIN.add("BIRDS");ORIGIN.add("DOGS");ORIGIN.add("REPTILES");ORIGIN.add("CATS");
        if(DEBUG) System.out.println("删除种类");
        if(DEBUG)System.out.println(categoryIdList);
        for (int i = 0; i < categoryIdList.size(); i++) {
            if(Constants.KEEP_ORIGIN_MODE){
                if(ORIGIN.contains(categoryIdList.get(i))) continue;
            }
            catalogService.removeCategory(categoryIdList.get(i));
        }
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //添加种类
    @PostMapping("/management/categories")
    public ReturnPack addCategories(@RequestBody Category category){
        if(DEBUG) System.out.println("添加种类");
        if(DEBUG) System.out.println(category);
        if(category.getDescription() == null) category.setDescription("暂无图片");

        if(catalogService.getCategory(category.getCategoryId()) != null)
            return ReturnPack.fail("已存在同样的ID");
        if(category.getCategoryId().equals(""))
            return ReturnPack.fail("id不能为空");
        if(category.getName().equals(""))
            return ReturnPack.fail("name不能为空");

        catalogService.insertCategory(category);
        JSONObject data = new JSONObject();
        data.put("category",category);
        return ReturnPack.success(data);
    }

    //todo 修改种类
    @PutMapping("/management/categories")
    public ReturnPack editCategories(@RequestBody Category category){
        if(DEBUG) System.out.println("修改种类");
        if(DEBUG) System.out.println(category);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //删除产品类型
    @DeleteMapping("/management/products")
    public ReturnPack deleteProduct(@RequestBody List<String> productIdList){
        if(DEBUG) System.out.println("删除产品类型");
        if(DEBUG) System.out.println(productIdList);
        for (int i = 0; i < productIdList.size(); i++) {
            catalogService.removeProduct(productIdList.get(i));
        }
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //添加产品类型
    @PostMapping("/management/products")
    public ReturnPack addProduct(@RequestBody Product product){
        if(DEBUG) System.out.println("添加产品类型");
        if(DEBUG) System.out.println(product);
        if(product.getDescription() == null) product.setDescription("暂无图片");

        if(catalogService.getProduct(product.getProductId()) != null)
            return ReturnPack.fail("已存在同样的ID");
        if(product.getProductId().equals(""))
            return ReturnPack.fail("id不能为空");
        if(product.getName().equals(""))
            return ReturnPack.fail("name不能为空");
        if(product.getCategoryId().equals(""))
            return ReturnPack.fail("categoryId不能为空");

        catalogService.insertProduct(product);
        JSONObject data = new JSONObject();
        System.out.println(product);
        data.put("product",product);
        return ReturnPack.success(data);
    }

    //todo 修改产品类型
    @PutMapping("/management/products")
    public ReturnPack editProduct(@RequestBody Product product){
        if(DEBUG) System.out.println("修改产品类型");
        if(DEBUG) System.out.println(product);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //删除产品
    @DeleteMapping("/management/items")
    public ReturnPack deleteItem(@RequestBody List<String> itemList){
        if(DEBUG) System.out.println("删除产品");
        if(DEBUG) System.out.println(itemList);
        for (int i = 0; i < itemList.size(); i++) {
            catalogService.removeItem(itemList.get(i));
        }
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //添加产品
    @PostMapping("/management/items")
    public ReturnPack addItem(@RequestBody receiveItem receiveItem){
        if(DEBUG) System.out.println("添加产品");
        if(DEBUG) System.out.println(receiveItem.toString());
        BigDecimal price;
        try{
            price = new BigDecimal(receiveItem.getListPrice());
        }catch (Exception e){
            return ReturnPack.fail("不支持的价格类型");
        }
        if(catalogService.getItem(receiveItem.getItemId()) != null)
            return ReturnPack.fail("该产品ID已存在");
        Item item = new Item();
        item.setItemId(receiveItem.getItemId());
        item.setProductId(receiveItem.getProductId());
        item.setListPrice(price);
        item.setStatus("S");
        item.setProduct(catalogService.getProduct(receiveItem.getProductId()));
        catalogService.insertItem(item);
        JSONObject data = new JSONObject();
        data.put("item",item);
        return ReturnPack.success(data);
    }

    //todo 修改产品
    @PutMapping("/management/items")
    public ReturnPack editItem(@RequestBody Item item){
        if(DEBUG) System.out.println("修改产品");
        if(DEBUG) System.out.println(item);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }
}
