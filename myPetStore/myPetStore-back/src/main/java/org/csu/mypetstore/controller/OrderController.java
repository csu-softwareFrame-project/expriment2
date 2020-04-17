package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Payment;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;

    //查看个人订单列表
    @GetMapping("/orderLists")
    public ReturnPack viewOrderList(HttpServletRequest httpServletRequest,@RequestParam String username){
        return orderService.viewOrderList(httpServletRequest,username);
    }

    //查看指定订单
    @GetMapping("/orders")
    public ReturnPack viewOrder(HttpServletRequest httpServletRequest, @RequestParam String orderId){
        return orderService.viewOrder(httpServletRequest,orderId);
    }

    //前进到填写订单信息页面
//    @GetMapping("/commit_new_order")
//    public String commitNewOrder(){
//        return "order/NewOrdersForm";
//    }

    //生成订单，跳转到订单页面让用户确认
    @PostMapping("/newOrders")
    public ReturnPack confirmNewOrder(HttpServletRequest httpServletRequest,@RequestBody Map<String,String> map){
        String cardType = map.get("cardType");
        String creditCard = map.get("creditCard");
        String expiryDate = map.get("expiryDate");
        String billToFirstName = map.get("billToFirstName");
        String billToLastName = map.get("billToLastName");
        String billAddress1 = map.get("billAddress1");
        String billAddress2 = map.get("billAddress2");
        String billCity = map.get("billCity");
        String billState = map.get("billState");
        String billZip = map.get("billZip");
        String billCountry = map.get("billCountry");
        Payment payment = new Payment();
        payment.setCardType(cardType);
        payment.setCreditCard(creditCard);
        payment.setExpiryDate(expiryDate);
        Order order = new Order();
        order.setBillToFirstName(billToFirstName);
        order.setBillToLastName(billToLastName);
        order.setBillAddress1(billAddress1);
        order.setBillAddress2(billAddress2);
        order.setBillCity(billCity);
        order.setBillState(billState);
        order.setBillZip(billZip);
        order.setBillCountry(billCountry);
//        System.out.println(payment+"  "+order);
        return orderService.generateOrder(httpServletRequest, payment, order);
//        return "order/ConfirmOrder";
    }


    //最终提交订单.
    @PostMapping("/finalOrders")
    public ReturnPack finalConfirmOrder(HttpServletRequest httpServletRequest,@RequestBody Order order) {
        return orderService.confirmOrder(httpServletRequest,order);
    }
}
