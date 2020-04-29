package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManageSearchController {
    @Autowired
    CatalogService catalogService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;

//    //最上方的搜索
//    @GetMapping("/management/accounts/results")
//    public ReturnPack Search(@RequestParam String keyword){
//
//        return null;
//    }

    //查询种类
    @GetMapping("/management/categories/results")
    public ReturnPack searchForCategories(@RequestParam String keyword){
//        catalogService.searchProductList()
        JSONObject data = new JSONObject();
        data.put("catalogList",catalogService.searchCategoryList(keyword));
        return ReturnPack.success(data);
    }

    //查询产品类型
    @GetMapping("/management/products/results")
    public ReturnPack serachForProducts(@RequestParam String keyword){
        JSONObject data = new JSONObject();
        data.put("productList",catalogService.searchProductList(keyword));
        return ReturnPack.success(data);//根据关键字搜索结果并返回
    }

    //查询产品
    @GetMapping("/management/items/results")
    public ReturnPack searchForItems(@RequestParam String keyword){
        System.out.println(keyword);
        JSONObject data = new JSONObject();
        data.put("itemList",catalogService.searchItemList(keyword));
        return ReturnPack.success(data);
    }

    //查询账户
    @GetMapping("/management/accounts/results")
    public ReturnPack searchForAccounts(@RequestParam String keyword){
        System.out.println(keyword);
        JSONObject data = new JSONObject();
        data.put("accountList",accountService.searchAccountList(keyword));
        return ReturnPack.success(data);
    }

    @GetMapping("/management/orders/results")
    public ReturnPack searchForOrders(@RequestParam String keyword){
        System.out.println(keyword);
        JSONObject data = new JSONObject();
        data.put("orderList",orderService.searchOrderList(keyword));
        return ReturnPack.success(data);
    }

}
