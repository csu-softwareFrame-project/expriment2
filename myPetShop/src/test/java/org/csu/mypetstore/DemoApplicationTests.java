package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
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
    void testProduct(){
        List<Product> productList = catalogService.getProductListByCategory("BIRDS");
        System.out.println(productList.size());
    }

}
