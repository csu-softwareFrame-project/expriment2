package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageSearchController {
    @Autowired
    CatalogService catalogService;

    //最上方的搜索
    @GetMapping("/management/accounts/results")
    public ReturnPack Search(@RequestParam String keyword){

        return null;
    }

    //查询种类
    @GetMapping("/management/categories/results")
    public ReturnPack searchForCategories(@RequestParam String keyword){
//        catalogService.searchProductList()
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //查询产品类型

    //查询产品

    //查新账户

}
