package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CatalogService catalogService;

    @Test
    void contextLoads() {
        System.out.println("a");
    }

    @Test
    void testCategory(){
        Category c = catalogService.getCategory("birds");
        System.out.println(c.getName()+" :"+c.getDescription()+";\n id: "+c.getCategoryId()+", ");
    }//success

    @Test
    void testProductList(){
        List<Product> productList = catalogService.getProductListByCategory("BIRDS");
        System.out.println(productList.size());
    }//success

    @Test
    void testProduct(){
        Product product = catalogService.getProduct("FI-SW-01");
        System.out.println(product.getCategoryId());
    }//success

    @Test
    void testItemList(){
        List<Item> itemList = catalogService.getItemListByProductId("FI-SW-01");
        System.out.println(itemList.size());
    }//success

    @Test
    void testItem(){
        Item item = catalogService.getItem("EST-1");
        System.out.println(item.getAttribute1());
    }

}
