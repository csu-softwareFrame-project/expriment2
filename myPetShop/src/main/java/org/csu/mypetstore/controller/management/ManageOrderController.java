package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
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
public class ManageOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderManagement")
    public String viewOrderList(Model model){
        System.out.println("success");
        List<Order> orderList = orderService.searchOrderList("");
        model.addAttribute("orderList",orderList);
        return "management/orderManagement";
    }

    @GetMapping("/removeOrder")
    public void removeOrder(String orderIdList){
        System.out.println("success");
        System.out.println(orderIdList);
        String[] orderIdArray = orderIdList.split(",");
        for(int i=0;i<orderIdArray.length;i++){
            orderService.removeOrder(Integer.parseInt(orderIdArray[i]));
        }
    }
    
    @GetMapping("/getOrder")
    @ResponseBody
    public String getOrder(String orderId){
        System.out.println("success");
        System.out.println(orderId);
        Order order = orderService.getOrder(Integer.parseInt(orderId));
        String info = String.valueOf(order.getOrderId());
        info = info + '#' + order.getUsername();
        info = info + '#' + order.getOrderDate();
        info = info + '#' + order.getShipAddress1();
        info = info + '#' + order.getShipAddress2();
        info = info + '#' + order.getShipCity();
        info = info + '#' + order.getShipState();
        info = info + '#' + order.getShipZip();
        info = info + '#' + order.getShipCountry();
        info = info + '#' + order.getShipToFirstName();
        info = info + '#' + order.getShipToLastName();
        info = info + '#' + order.getBillAddress1();
        info = info + '#' + order.getBillAddress2();
        info = info + '#' + order.getBillCity();
        info = info + '#' + order.getBillState();
        info = info + '#' + order.getBillZip();
        info = info + '#' + order.getBillCountry();
        info = info + '#' + order.getBillToFirstName();
        info = info + '#' + order.getBillToLastName();
        info = info + '#' + order.getCourier();
        info = info + '#' + order.getTotalPrice();
        info = info + '#' + order.getCreditCard();
        info = info + '#' + order.getCardType();
        info = info + '#' + order.getExpiryDate();
        info = info + '#' + order.getLocale();
        info = info + '#' + order.getStatus();
        System.out.println(info);
        return info;
    }
}
