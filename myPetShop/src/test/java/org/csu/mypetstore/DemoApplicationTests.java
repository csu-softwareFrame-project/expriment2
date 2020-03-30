package org.csu.mypetstore;

import org.csu.mypetstore.domain.Account;
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

    @Test
    void testAccountByUsername()
    {
        Account account = accountService.getAccount("233");
        System.out.println(account.getUsername() + "," + account.getLanguagePreference());
        System.out.println(account.isListOption());
    }

    @Test
    void testAccountByUsernameAndPassword()
    {
        Account account = accountService.getAccount("233","123");
        System.out.println(account.getLanguagePreference());
    }

    @Test
    void testAccountInsert()
    {
        Account account = new Account();
        account.setUsername("wofule");
        account.setPassword("abaaba");
        account.setLanguagePreference("Chinese");
        account.setEmail("777");
        account.setFirstName("Z");
        account.setLastName("J");
        account.setStatus("55");
        account.setBannerOption(true);
        account.setListOption(false);
        account.setFavouriteCategoryId("BIRDS");
        account.setCountry("China");
        account.setZip("FJ");
        account.setState("FZ");
        account.setCity("FZ");
        account.setAddress2("FZYZ");
        account.setAddress1("CSU");
        account.setPhone("8848");
        account.setBannerName("CATS");
        accountService.insertAccount(account);
    }

    @Test
    void testAccountUpdate()
    {
        Account account = new Account();
        account.setUsername("hahaha");
        account.setPassword("999");
        account.setLanguagePreference("Chinese");
        account.setEmail("888");
        account.setFirstName("Z");
        account.setLastName("J");
        account.setStatus("55");
        account.setBannerOption(false);
        account.setListOption(false);
        account.setFavouriteCategoryId("BIRDS");
        account.setCountry("China");
        account.setZip("FJ");
        account.setState("FZ");
        account.setCity("FZ");
        account.setAddress2("FZYZ");
        account.setAddress1("CSU");
        account.setPhone("8848");
        account.setBannerName("CATS");

        accountService.updateAccount(account);
    }
}
