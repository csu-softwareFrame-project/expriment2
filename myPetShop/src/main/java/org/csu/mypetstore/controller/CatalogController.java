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

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/view")
    public String view(){
        return "catalog/main.html";
    }

    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model){
        if (categoryId != null){
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("productList", productList);
            return "catalog/category.html";
        }
        return "";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(String productId, Model model){
        if (productId != null){
            System.out.println("a");
            Product product = catalogService.getProduct(productId);
            List<Item> itemList = catalogService.getItemListByProductId(productId);
            model.addAttribute("product", product);
            model.addAttribute("itemList", itemList);
            return "catalog/product.html";
        }
        return "";
    }
}
