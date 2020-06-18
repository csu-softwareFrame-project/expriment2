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

import java.math.BigDecimal;
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
        List<Product> productList = new ArrayList<>();
        List<Product> temp = new ArrayList<>();
        List<Category> categoryList = catalogService.getCategoryList();
        for(int i=0;i<categoryList.size();i++){
            temp = catalogService.getProductListByCategory(categoryList.get(i).getCategoryId());
            for(int j=0;j<temp.size();j++){
                productList.add(temp.get(j));
            }
        }
        model.addAttribute("productList",productList);
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

    @GetMapping("/updateProduct")
    public void updateProduct(String productInfo){
        String[] info = productInfo.split(",");
        Product product = new Product();
        product.setProductId(info[0]);
        product.setName(info[1]);
        product.setCategoryId(info[2]);
        if(info.length>3){
            product.setDescription(catalogService.getProduct(info[3]).getDescription());
            catalogService.updateProduct(product,info[3]);
        }else{
            product.setDescription("");
            catalogService.insertProduct(product);
        }
    }

    @GetMapping("/removeItem")
    public void removeItem(String itemList){
        String[] info = itemList.split(",");
        for(int i=0;i<info.length;i++){
            catalogService.removeItem(info[i]);
        }
    }

    @GetMapping("/getItem")
    @ResponseBody
    public String getItem(String itemId){
        Item item = catalogService.getItem(itemId);
        String info = item.getItemId();
        info = info + "#" + item.getProduct().getProductId();
        info = info + "#" + item.getQuantity();
        info = info + "#" + item.getListPrice();
        info = info + "#" + item.getUnitCost();
        info = info + "#" + item.getSupplierId();
        info = info + "#" + item.getStatus();
        info = info + "#" + item.getAttribute1();
        info = info + "#" + item.getAttribute2();
        info = info + "#" + item.getAttribute3();
        info = info + "#" + item.getAttribute4();
        info = info + "#" + item.getAttribute5();
        return info;
    }

    @GetMapping("/updateItem")
    public void updateItem(String itemInfo){
        String[] info = itemInfo.split(",");
        System.out.println(info.length);
        Item item = new Item();
        item.setItemId(info[0]);
        item.setProductId(info[1]);
        item.setProduct(catalogService.getProduct(info[1]));
        item.setQuantity(Integer.parseInt(info[2]));
        item.setListPrice(new BigDecimal(info[3]));
        item.setUnitCost(new BigDecimal(info[4]));
        item.setSupplierId(Integer.parseInt(info[5]));
        item.setStatus(info[6]);
        item.setAttribute1(info[7]);
        item.setAttribute2(info[8]);
        item.setAttribute3(info[9]);
        item.setAttribute4(info[10]);
        item.setAttribute5(info[11]);

        if(info.length>12){
            catalogService.updateItem(item,info[12]);
        }else{
            catalogService.insertItem(item);
        }
    }
}
