package org.csu.mypetstore.service;

import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private LineItemMapper lineItemMapper;

    public void insertOrder(Order order) {  //新建订单
        order.setOrderId(getNextId("ordernum"));  //根据队列情况自动生成订单号
        for (int i = 0; i < order.getLineItems().size(); i++) {  //对订单内所有的产品进行库存更新
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            int increment = lineItem.getQuantity();  //注意此处设置的数量是购买的数量而不是库存数量
            itemMapper.updateInventoryQuantity(increment,itemId);  //更新产品库存数量
        }

        orderMapper.insertOrder(order);  //新建订单
        orderMapper.insertOrderStatus(order);  //设置订单状态
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());  //对订单内的产品设置订单号
            lineItemMapper.insertLineItem(lineItem);  //新建订单的产品内容
        }
    }

    //获取订单
    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);  //获取订单基本信息
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));  //获取订单的产品信息

        for (int i = 0; i < order.getLineItems().size(); i++) {  //获取订单内产品详情（一般用不到）
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(orderMapper.getOrderItemQuantity(orderId,item.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    //根据用户名获取订单
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orderList = orderMapper.getOrdersByUsername(username);
        Order order = new Order();
        for(int i=0;i<orderList.size();i++)
        {
            orderList.get(i).setLineItems(lineItemMapper.getLineItemsByOrderId(orderList.get(i).getOrderId()));
            for(int j=0;j<orderList.get(i).getLineItems().size();j++)
            {
                LineItem lineItem = (LineItem) orderList.get(i).getLineItems().get(j);
                Item item = itemMapper.getItem(lineItem.getItemId());
                item.setQuantity(orderMapper.getOrderItemQuantity(orderList.get(i).getOrderId(),item.getItemId()));
                lineItem.setItem(item);
            }
        }

        return orderList;
    }

    public int getOrderItemQuantity(int orderId,String itemId) {
        return orderMapper.getOrderItemQuantity(orderId,itemId);
    }

    //获取订单号，name填ordernum；name填要生成的号码类型，有"ordernum"和"linenum".
    public int getNextId(String name) {
        Sequence sequence = new Sequence(name, -1);
        sequence = (Sequence) sequenceMapper.getSequence(sequence);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
                    + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);  //获得新的订单号
        sequenceMapper.updateSequence(parameterObject);  //更新订单号序列
        return sequence.getNextId();
    }

    //获取用户的订单列表
    public void viewOrderList(HttpSession session,Map<String,Object> map){
       Account account =(Account) session.getAttribute("loginUser");
       List<Order> orderList = getOrdersByUsername(account.getUsername());
        if(Constants.DEBUG_SERVICE && !orderList.isEmpty())System.out.println("订单日期:"+orderList.get(0).getOrderDate());
        if(Constants.DEBUG_SERVICE && orderList.isEmpty()) System.out.println(account.getUsername()+"的订单表为空");
       map.put("orderList",orderList);
    }

    //查看具体订单信息
    public void viewOrder(Map<String,Object> map,String orderId){
        Order order = getOrder(Integer.parseInt(orderId));
        if(Constants.DEBUG_SERVICE)System.out.println("订单属性:"+order.toString());
        map.put("order",order);
    }
}
