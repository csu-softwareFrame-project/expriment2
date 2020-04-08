package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Payment;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;

    //查看个人订单列表
    @GetMapping("/view_orderList")
    public String viewOrderList(HttpSession session, Map<String,Object> map){
        orderService.viewOrderList(session,map);
        return "order/ListOrders";
    }

    //查看指定订单
    @GetMapping("/view_order")
    public String viewOrder(Map<String,Object> map,String orderId){
        orderService.viewOrder(map, orderId);
        return "order/viewOrder";
    }

    //前进到填写订单信息页面
    @GetMapping("/commit_new_order")
    public String commitNewOrder(){
        return "order/NewOrdersForm";
    }

    //生成订单，跳转到订单页面让用户确认
    @PostMapping("confirm_new_order")
    public String confirmNewOrder(HttpSession session, Payment payment,Order order){
        session.setAttribute("order",orderService.generateOrder(session, payment, order));
        return "order/ConfirmOrder";
    }


    //最终提交订单。使用重定向，避免重复提交订单
    @GetMapping("/final_confirm_order")
    public String finalConfirmOrder(HttpSession session) {
        return "redirect:view_order?orderId="+orderService.confirmOrder(session);
    }
}
