package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


/**
 * @desciption:目录相关跳转
 * @Date:2020.4.1
 */

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    //主目录跳转
    //@GetMapping("/viewCategory")
//    public String viewCategory(String categoryId, Model model){
//        //如果有选定宠物类型，展示该类型的菜单；否则展示宠物类型菜单
//        if (catalogService.viewCategoryService(categoryId,model)){
//            return "catalog/category";
//        }else{
//            return "catalog/category";
//        }
//    }

    //product目录跳转
//    @GetMapping("/viewProduct")
//    public String viewProduct(String productId, Model model){
//        if (productId != null){
//            System.out.println("a");
//            Product product = catalogService.getProduct(productId);
//            List<Item> itemList = catalogService.getItemListByProductId(productId);
//            model.addAttribute("product", product);
//            model.addAttribute("itemList", itemList);
//            return "catalog/product";
//        }
//        return "";
//    }

    //Item目录跳转
    @GetMapping("/viewItem")
    public String viewItem(String itemId, Model model){
        if (itemId != null){
            System.out.println("a");
            Item item = catalogService.getItem(itemId);
            model.addAttribute("item", item);
            return "catalog/item";
        }
        return "";
    }

    //todo 会丢失css和js样式
    //利用springboot精准匹配的原则处理404
    @RequestMapping("**")
    public String handle404(){
        return "common/error";
    }
}