package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @desciption:主页相关跳转
 * @Date:2020.4.1
 */
@RestController
public class MainPageController {

    @Autowired
    CatalogService catalogService;


    //搜索功能
    @GetMapping("/results")
    public ReturnPack search(@RequestParam String keyword){
        JSONObject data = new JSONObject();
        data.put("productList",catalogService.searchProductList(keyword));
        return ReturnPack.success(data);//根据关键字搜索结果并返回
    }
}
