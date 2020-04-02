package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Sequence;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            int increment = lineItem.getQuantity();
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

    public Order getOrder(int orderId) {  //获取订单
        Order order = orderMapper.getOrder(orderId);  //获取订单基本信息
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));  //获取订单的产品信息

        for (int i = 0; i < order.getLineItems().size(); i++) {  //获取订单内产品详情（一般用不到）
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }  //根据用户名获取订单

    public int getNextId(String name) {  //获取订单号，name填ordernum
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
}
