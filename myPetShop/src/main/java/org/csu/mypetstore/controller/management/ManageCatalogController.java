package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/management")
public class ManageCatalogController {
    @Autowired
    public CatalogService catalogService;

    @GetMapping("/categoryManagement")
    public String viewCategory(Model model){
        System.out.println("success");
        List<Category> categoryList = catalogService.getCategoryList();
        model.addAttribute("categoryList",categoryList);
        return "management/categoryManagement";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Model model,String categoryId){
        System.out.println("success");
        List<Product> productList = catalogService.getProductListByCategory(categoryId);
        model.addAttribute("productList",productList);
        List<Category> categoryList = catalogService.getCategoryList();
        model.addAttribute("categoryList",categoryList);
        return "management/productManagement";
    }

    @GetMapping("/viewItem")
    public String viewItem(Model model,String productId){
        List<Item> itemList = catalogService.getItemListByProductId(productId);
        model.addAttribute("itemList",itemList);
        return "management/itemManagement";
    }

    @GetMapping("/removeCategory")
    public void removeCategory(String categoryList){
        System.out.println("success");
        System.out.println(categoryList);
        String[] categoryIdArray = categoryList.split(",");
        for(int i=0;i<categoryIdArray.length;i++){
            catalogService.removeCategory(categoryIdArray[i]);
        }
    }

    @GetMapping("/getCategory")
    @ResponseBody
    public String getCategory(String categoryId){
        Category category = catalogService.getCategory(categoryId);
        String info = category.getCategoryId();
        info = info + "#" + category.getName();
        return info;
    }

    @GetMapping("/updateCategory")
    public void updateCategory(String categoryInfo){
        String[] info = categoryInfo.split(",");
        Category category = new Category();
        category.setCategoryId(info[0]);
        category.setName(info[1]);
        if(info.length>2){
            category.setDescription(catalogService.getCategory(info[2]).getDescription());
            catalogService.updateCategory(category,info[2]);
        }else{
            category.setDescription("");
            catalogService.insertCategory(category);
        }
    }

    @GetMapping("/removeProduct")
    public void removeProduct(String productList){
        System.out.println("success");
        System.out.println(productList);
        String[] info = productList.split(",");
        for(int i=0;i<info.length;i++){
            catalogService.removeProduct(info[i]);
        }
    }

    @GetMapping("/getProduct")
    @ResponseBody
    public String getProduct(String productId){
        System.out.println("success");
        System.out.println(productId);
        Product product = catalogService.getProduct(productId);
        String info = product.getProductId();
        info = info + "#" + product.getName() + "#" + product.getCategoryId();
        return info;
    }
}
