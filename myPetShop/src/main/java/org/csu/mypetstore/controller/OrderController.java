package org.csu.mypetstore.controller;

import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/view_orderList")
    public String viewOrderList(HttpSession session, Map<String,Object> map){
        orderService.viewOrderList(session,map);
        return "order/ListOrders";
    }

    @GetMapping("/view_order")
    public String viewOrder(Map<String,Object> map,String orderId){
        orderService.viewOrder(map, orderId);
        return "order/viewOrder";
    }
}
