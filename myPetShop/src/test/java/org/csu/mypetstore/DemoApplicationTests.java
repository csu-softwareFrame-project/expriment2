package org.csu.mypetstore;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.MailService;
import org.csu.mypetstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class DemoApplicationTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private MailService mailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Test
    void contextLoads() {
        System.out.println("a");
    }

    @Test
    public void testSend(){
//        System.out.println(mailService.sendSimpleMail(););
        mailService.sendSimpleMail("a123213090@126.com","test", "just a test");
    }

    @Test
    void testCategory(){
        Category c = catalogService.getCategory("CATS");
        System.out.println(c.getName()+" :"+c.getDescription()+";\n id: "+c.getCategoryId()+", ");
    }//success

    @Test
    void testProductList(){
        List<Product> productList = catalogService.getProductListByCategory("DOGS");
        System.out.println(productList.size());
    }//success
    

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
        List<Product> productList = catalogService.searchProductList("a");        System.out.println(productList.size());
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
        System.out.println(item.getProduct().getProductId() + "," + item.getListPrice() + "," + item.getAttribute1());
    }

    @Test
    void testUpdateQuantity()
    {
        catalogService.updateInventoryQuantity(20,"EST-10");
    }

    @Test
    void testAccountByUsername()
    {
        Account account = accountService.getAccount("99");
        System.out.println(account.getUsername() + "," + account.getLanguagePreference() + "," + account.getBannerName());
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
        account.setUsername("99");
        account.setPassword("abaaba");
        account.setLanguagePreference("English");
        account.setEmail("777");
        account.setFirstName("Z");
        account.setLastName("J");
        account.setStatus("55");
        //account.setBannerOption(true);
        //account.setListOption(false);
        //account.setFavouriteCategoryId("BIRDS");
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

    @Test
    void testOrderInsert()
    {
        Order order = new Order();
        order.setShipCountry("China");
        order.setShipZip("FJ");
        order.setShipState("FZ");
        order.setShipCity("FZYZ");
        order.setShipAddress1("FZYZ");
        order.setShipAddress2("CSU");
        order.setShipToFirstName("Z");
        order.setShipToLastName("J");
        order.setTotalPrice(new BigDecimal("998"));
        order.setUsername("hahaha");
        order.setOrderDate(new Date());
        order.setBillCountry("China");
        order.setBillZip("FJ");
        order.setBillState("FZ");
        order.setBillCity("FZYZ");
        order.setBillAddress1("FZYZ");
        order.setBillAddress2("CSU");
        order.setBillToFirstName("Z");
        order.setBillToLastName("J");
        order.setExpiryDate("unknown");
        order.setCreditCard("IKEA");
        order.setCardType("CSA");
        order.setStatus("0");
        order.setLocale("lalalal");
        order.setCourier("Bob");
        order.setOrderId(996);
        orderService.insertOrder(order);
    }

    @Test
    void testOrderGet()
    {
        Order order = orderService.getOrder(1001);
        System.out.println(order.getExpiryDate() + "," + order.getLineItems().get(0).getItemId());
    }

    @Test
    void testOrderGetByUsername()
    {
        List<Order> orderList = orderService.getOrdersByUsername("hahaha");
        System.out.println(orderList.size() + "," + orderList.get(0).getShipState());
    }

    @Test
    void testNestIdGet()
    {
        int nextId = orderService.getNextId("ordernum");
        System.out.println(nextId);
    }

    @Test
    void testCartItemListGet()
    {
        List<CartItem> cartItemList = cartService.getCartItemListByUsername("233");
        System.out.println(cartItemList.size());
    }

    @Test
    void testCartItemQuantityGet()
    {
        int quantity = cartService.getQuantity("233","EST-1");
        System.out.println(quantity);
    }

    @Test
    void testCartItemInsert()
    {
        CartItem cartItem = new CartItem();
        Item item = catalogService.getItem("EST-10");
        cartItem.setItem(item);
        cartItem.setInStock(true);
        cartItem.setQuantity(5);
        cartService.insertCartItem("233",cartItem);
    }

    @Test
    void testCartItemQuantityUpdate()
    {
        cartService.updateCartItemQuantity("233","EST-1",6);
    }

    @Test
    void testCartItemRemove()
    {
        cartService.removeCartItem("233","EST-10");
    }

    @Test
    void testCartItemClear()
    {
        cartService.clear("a");
    }
}
