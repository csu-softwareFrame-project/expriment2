package org.csu.mypetstore.controller.management;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
