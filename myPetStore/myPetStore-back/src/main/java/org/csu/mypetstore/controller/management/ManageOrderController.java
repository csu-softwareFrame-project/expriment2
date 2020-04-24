package org.csu.mypetstore.controller.management;

import com.alibaba.fastjson.JSONObject;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageOrderController {

    //查询订单


    //获取所有订单信息
    @GetMapping("/management/orders")
    public ReturnPack getOrders(){
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //删除订单
    @DeleteMapping("/management/orders")
    public ReturnPack deleteOrders(){
        JSONObject data = new JSONObject();
        return ReturnPack.success(data);
    }

    //发货
}
