package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
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
        Category c = catalogService.getCategory("CATS");
        System.out.println(c.getName()+" :"+c.getDescription()+";\n id: "+c.getCategoryId()+", ");
    }

    @Test
    void testProductList(){
        List<Product> productList = catalogService.getProductListByCategory("DOGS");
        System.out.println(productList.size());
    }

    @Test
    void testCategoryList()
    {
        List<Category> categoryList = catalogService.getCategoryList();
        System.out.println(categoryList.size());
    }

    @Test
    void testProduct()
    {
        Product product = catalogService.getProduct("FI-SW-01");
        System.out.println(product.getDescription() + "," + product.getCategoryId());
    }

    @Test
    void testSearchProductList()
    {
        List<Product> productList = catalogService.searchProductList("a");
        System.out.println(productList.size());
    }

    @Test
    void testItemList()
    {
        List<Item> itemList = catalogService.getItemListByProduct("RP-SN-01");
        System.out.println(itemList.size());
    }

    @Test
    void testItemQuantity()
    {
        int quantity = catalogService.getInventoryQuantity("EST-10");
        System.out.println(quantity);
    }

    @Test
    void testItem()
    {
        Item item = catalogService.getItem("EST-10");
        System.out.println(item.getUnitCost() + "," + item.getListPrice() + "," + item.getAttribute1());
    }

    @Test
    void testUpdateQuantity()
    {
        catalogService.updateInventoryQuantity(20,"EST-10");
    }
}
