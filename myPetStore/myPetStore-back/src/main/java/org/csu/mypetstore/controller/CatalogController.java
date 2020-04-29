package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/catalog")
public class CatalogController {
    private boolean DEBUG = true;
    @Autowired
    private CatalogService catalogService;

    //主目录跳转
    @GetMapping("/categories")
    public ReturnPack viewCategory(HttpServletRequest httpServletRequest, @RequestParam String categoryId){
        JSONObject data = new JSONObject();
        return catalogService.viewCategoryService(httpServletRequest,categoryId,data);
    }

    //product目录跳转
    @GetMapping("/products")
    public ReturnPack viewProduct(HttpServletRequest httpServletRequest,@RequestParam String productId){
        JSONObject data = new JSONObject();
        Product product = catalogService.getProduct(productId);
        List<Item> itemList = catalogService.getItemListByProductId(productId);
        itemList.removeIf(item -> item.getStatus().equals("S"));//排除下架的货物
        data.put("product", product);
        data.put("itemList", itemList);
        return ReturnPack.success(data);
    }

    //Item目录跳转
    @GetMapping("/items")
    public ReturnPack viewItem(HttpServletRequest httpServletRequest,@RequestParam String itemId){
        JSONObject data = new JSONObject();
        Item item = catalogService.getItem(itemId);
        data.put("item", item);
        return ReturnPack.success(data);
    }

}
