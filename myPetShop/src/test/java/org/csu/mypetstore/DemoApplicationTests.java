package org.csu.mypetstore;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Test
    void contextLoads() {
        System.out.println("a");
    }

    @Test
    void testCategory(){  //测试通过
        Category c = catalogService.getCategory("CATS");
        String info = c.toString();
        System.out.println(info);
    }//success

    @Test
    void testProductList(){  //测试通过
        List<Product> productList = catalogService.getProductListByCategory("DOGS");
        System.out.println(productList.size());
    }//success


    @Test
    void testCategoryList()  //测试通过
    {
        List<Category> categoryList = catalogService.getCategoryList();
        System.out.println(categoryList.size());
    }

    @Test
    void testProduct()  //测试通过
    {
        Product product = catalogService.getProduct("FI-SW-01");
        String info = product.toString();
        System.out.println(info);
    }

    @Test
    void testSearchProductList()  //测试通过
    {
        List<Product> productList = catalogService.searchProductList("a");
        System.out.println(productList.size());
    }

    @Test
    void testItemList()  //测试通过
    {
        List<Item> itemList = catalogService.getItemListByProduct("RP-SN-01");
        System.out.println(itemList.size());
    }

    @Test
    void testItemQuantity()  //测试通过
    {
        int quantity = catalogService.getInventoryQuantity("EST-10");
        System.out.println(quantity);
    }

    @Test
    void testItem()  //测试通过
    {
        Item item = catalogService.getItem("EST-10");
        String info = item.toString();
        System.out.println(info);
    }

    @Test
    void testUpdateQuantity()
    {
        catalogService.updateInventoryQuantity(20,"EST-10");
    }  //测试通过

    @Test
    void testAccountByUsername()  //测试通过
    {
        Account account = accountService.getAccount("233");
    }

    @Test
    void testAccountByUsernameAndPassword()  //测试通过
    {
        Account account = accountService.getAccount("233","123");
        String info = account.toString();
        System.out.println(info);
    }

    @Test
    void testAccountInsert()  //测试通过
    {
        Account account = new Account();
        account.setUsername("66");
        account.setPassword("abaaba");
        account.setLanguagePreference("English");
        account.setEmail("777");
        account.setFirstName("Z");
        account.setLastName("J");
        account.setStatus("55");
        account.setBannerOption("1");
        account.setListOption("0");
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
    void testAccountUpdate()  //测试通过
    {
        Account account = new Account();
        account.setUsername("hahaha");
        account.setPassword("888");
        account.setLanguagePreference("English");
        account.setEmail("999");
        account.setFirstName("J");
        account.setLastName("Z");
        account.setStatus("5");
        account.setBannerOption("1");
        account.setListOption("1");
        account.setFavouriteCategoryId("DOGS");
        account.setCountry("Japan");
        account.setZip("FJF");
        account.setState("FZF");
        account.setCity("FZF");
        account.setAddress2("FZYZF");
        account.setAddress1("CSUF");
        account.setPhone("88488");
        account.setBannerName("BIRDS");

        accountService.updateAccount(account);
    }

    @Test
    void testOrderInsert()  //测试通过
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
        order.setUsername("5545");
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

        LineItem lineItem = new LineItem();
        lineItem.setLineNumber(997);
        lineItem.setOrderId(996);
        Item item = catalogService.getItem("EST-15");
        lineItem.setItem(item);
        lineItem.setUnitPrice(item.getUnitCost());
        lineItem.setQuantity(20);
        lineItem.setItemId(item.getItemId());
        List<LineItem> lineItemList = new ArrayList<>();
        lineItemList.add(lineItem);
        order.setLineItems(lineItemList);

        orderService.insertOrder(order);
    }

    @Test
    void testOrderGet()  //测试通过
    {
        Order order = orderService.getOrder(1006);
        System.out.println(order.getLineItems().get(0).getItem().getQuantity());

        System.out.println(order.getLineItems().size());
        System.out.println(order.getLineItems().get(0).getUnitPrice());
        System.out.println(order.getLineItems().get(0).getLineNumber());
        System.out.println(order.getLineItems().get(0).getItemId());
        System.out.println(order.getLineItems().get(0).getOrderId());
        System.out.println(order.getLineItems().get(0).getQuantity());
        System.out.println(order.getLineItems().get(0).getTotal());
        System.out.println(order.getOrderId());
        System.out.println(order.getExpiryDate());
        System.out.println(order.getStatus());
        System.out.println(order.getLocale());
        System.out.println(order.getCardType());
        System.out.println(order.getCreditCard());
        System.out.println(order.getTotalPrice());
        System.out.println(order.getCourier());
        System.out.println(order.getOrderDate());
        System.out.println(order.getUsername());

        System.out.println(order.getBillAddress1());
        System.out.println(order.getBillToLastName());
        System.out.println(order.getBillAddress2());
        System.out.println(order.getBillToFirstName());
        System.out.println(order.getBillCountry());
        System.out.println(order.getBillZip());
        System.out.println(order.getBillState());
        System.out.println(order.getBillCity());

        System.out.println(order.getShipAddress1());
        System.out.println(order.getShipToLastName());
        System.out.println(order.getShipAddress2());
        System.out.println(order.getShipToFirstName());
        System.out.println(order.getShipCountry());
        System.out.println(order.getShipZip());
        System.out.println(order.getShipState());
        System.out.println(order.getShipCity());
    }

    @Test
    void testOrderGetByUsername()  //测试通过
    {
        List<Order> orderList = orderService.getOrdersByUsername("5545");
        System.out.println(orderList.get(0).getLineItems().get(0).getItem().getQuantity());
        System.out.println(orderList.size());

        System.out.println(orderList.get(0).getBillCountry());
        System.out.println(orderList.get(0).getBillZip());
        System.out.println(orderList.get(0).getBillState());
        System.out.println(orderList.get(0).getBillCity());
        System.out.println(orderList.get(0).getBillToFirstName());
        System.out.println(orderList.get(0).getBillToLastName());
        System.out.println(orderList.get(0).getBillAddress1());
        System.out.println(orderList.get(0).getBillAddress2());

        System.out.println(orderList.get(0).getShipCountry());
        System.out.println(orderList.get(0).getShipZip());
        System.out.println(orderList.get(0).getShipState());
        System.out.println(orderList.get(0).getShipCity());
        System.out.println(orderList.get(0).getShipToFirstName());
        System.out.println(orderList.get(0).getShipToLastName());
        System.out.println(orderList.get(0).getShipAddress1());
        System.out.println(orderList.get(0).getShipAddress2());

        System.out.println(orderList.get(0).getUsername());
        System.out.println(orderList.get(0).getOrderDate());
        System.out.println(orderList.get(0).getCourier());
        System.out.println(orderList.get(0).getTotalPrice());
        System.out.println(orderList.get(0).getCreditCard());
        System.out.println(orderList.get(0).getCardType());
        System.out.println(orderList.get(0).getLocale());
        System.out.println(orderList.get(0).getExpiryDate());
        System.out.println(orderList.get(0).getOrderId());
        System.out.println(orderList.get(0).getStatus());
    }

    @Test
    void testNestIdGet()  //测试通过
    {
        int nextId = orderService.getNextId("ordernum");
        System.out.println(nextId);
    }

    @Test
    void testCartItemListGet()  //测试通过
    {
        List<CartItem> cartItemList = cartService.getCartItemListByUsername("233");
        System.out.println(cartItemList.size());

        System.out.println(cartItemList.get(0).getItemId());
        System.out.println(cartItemList.get(0).getQuantity());
        System.out.println(cartItemList.get(0).getTotal());
    }

    @Test
    void testCartItemQuantityGet()  //测试通过
    {
        int quantity = cartService.getQuantity("233","EST-1");
        System.out.println(quantity);
    }

    @Test
    void testCartItemInsert()  //测试通过
    {
        CartItem cartItem = new CartItem();
        Item item = catalogService.getItem("EST-12");
        cartItem.setItem(item);
        cartItem.setInStock(true);
        cartItem.setQuantity(5);
        cartService.insertCartItem("233",cartItem);
    }

    @Test
    void testCartItemQuantityUpdate()
    {
        cartService.updateCartItemQuantity("233","EST-1",6);
    }  //测试通过

    @Test
    void testCartItemRemove()
    {
        cartService.removeCartItem("233","EST-12");
    }  //测试通过

    @Test
    void testCartItemClear()
    {
        cartService.clear("233");
    }  //测试通过

}
