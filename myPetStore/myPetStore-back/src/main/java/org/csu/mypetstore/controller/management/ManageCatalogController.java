package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManageCatalogController {
    private static final boolean DEBUG = true;

    @Autowired
    AccountService accountService;


    //todo 删除种类
    @DeleteMapping("/management/categories")
    public ReturnPack deleteCategories(@RequestBody List<String> categoryList){
        if(DEBUG) System.out.println("删除种类");
        if(DEBUG)System.out.println(categoryList);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //todo 添加种类
    @PostMapping("/management/categories")
    public ReturnPack addCategories(@RequestBody Category category){
        if(DEBUG) System.out.println("添加种类");
        if(DEBUG) System.out.println(category);
        JSONObject data = new JSONObject();
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

    //todo 删除产品类型
    @DeleteMapping("/management/products")
    public ReturnPack deleteProduct(@RequestBody Product product){
        if(DEBUG) System.out.println("删除产品类型");
        if(DEBUG) System.out.println(product);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //todo 添加产品类型
    @PostMapping("/management/products")
    public ReturnPack addProduct(@RequestBody Product product){
        if(DEBUG) System.out.println("添加产品类型");
        if(DEBUG) System.out.println(product);
        JSONObject data = new JSONObject();
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

    //todo 删除产品
    @DeleteMapping("/management/items")
    public ReturnPack deleteItem(@RequestBody Item item){
        if(DEBUG) System.out.println("删除产品");
        if(DEBUG) System.out.println(item);
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //todo 添加产品
    @PostMapping("/management/items")
    public ReturnPack addItem(@RequestBody Item item){
        if(DEBUG) System.out.println("添加产品");
        if(DEBUG) System.out.println(item);
        JSONObject data = new JSONObject();
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
