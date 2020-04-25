package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ManageOrderController {

    @Autowired
    OrderService orderService;

    //查询订单


    //获取所有订单信息
    @GetMapping("/management/orders")
    public ReturnPack getOrders(@RequestParam String type){
        List<Order> orderList = new LinkedList<>();
        if(type.equals("1")){
            orderList.addAll(orderService.getOrdersByStatus(0));
        } else if(type.equals("2")){
            orderList.addAll(orderService.getOrdersByStatus(1));
        } else if(type.equals("3")){
            orderList.addAll(orderService.getOrdersByStatus(0));
            orderList.addAll(orderService.getOrdersByStatus(1));
        }
        JSONObject data = new JSONObject();
        data.put("orderList",orderList);
        return ReturnPack.success(data);
    }

    //删除订单
    @DeleteMapping("/management/orders")
    public ReturnPack deleteOrders(@RequestBody List<String> orderIdList){
        for (int i = 0; i < orderIdList.size(); i++) {
            orderService.removeOrder(Integer.parseInt(orderIdList.get(i)));
        }
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //发货
}
