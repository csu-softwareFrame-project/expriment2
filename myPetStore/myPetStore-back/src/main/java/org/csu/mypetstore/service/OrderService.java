package org.csu.mypetstore.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.Car;
import org.csu.mypetstore.Constants;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.persistence.SequenceMapper;
import org.csu.mypetstore.util.JwtUtil;
import org.csu.mypetstore.util.ReturnPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    CatalogService catalogService;

    //根据Order参数，将订单信息插入数据库并返回订单id
    public int insertOrder(Order order) {
        order.setOrderId(getNextId("ordernum"));  //根据队列情况自动生成订单号
        for (int i = 0; i < order.getLineItems().size(); i++) {  //对订单内所有的产品进行库存更新
            LineItem lineItem = order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            int increment = lineItem.getQuantity();  //注意此处设置的数量是购买的数量而不是库存数量
            itemMapper.updateInventoryQuantity(increment,itemId);  //更新产品库存数量
            if(Constants.REDIS_MODE){
                if(Constants.REDIS_MODE){
                    Jedis jedis = new Jedis("localhost", 6379);
                    jedis.select(1);
                    Item item = catalogService.getItem(itemId);
                    item.setQuantity(item.getQuantity()-increment);
                    Map<String,String> itemMap = jedis.hgetAll("item");
                    itemMap.put(item.getItemId(), JSON.toJSONString(item));
                    jedis.hmset("item",itemMap);
                }
            }
        }

        orderMapper.insertOrder(order);  //新建订单
        orderMapper.insertOrderStatus(order);  //设置订单状态
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());  //对订单内的产品设置订单号
            lineItemMapper.insertLineItem(lineItem);  //新建订单的产品内容
        }
        return order.getOrderId();
    }

    //获取订单
    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);  //获取订单基本信息
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));  //获取订单的产品信息
        BigDecimal bd = new BigDecimal("0");
        for (int i = 0; i < order.getLineItems().size(); i++) {  //获取订单内产品详情（一般用不到）
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            int quantity  = orderMapper.getOrderItemQuantity(orderId,item.getItemId());
            item.setQuantity(quantity);
            //todo 总额
            bd = bd.add(item.getListPrice().multiply(new BigDecimal(quantity+"")));
            lineItem.setItem(item);
        }
        order.setTotalPrice(bd);
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
    public ReturnPack viewOrderList(HttpServletRequest httpServletRequest,String username){
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        List<Order> orderList = getOrdersByUsername(username);
        if(Constants.DEBUG_SERVICE && !orderList.isEmpty())System.out.println("订单日期:"+orderList.get(0).getOrderDate());
        if(Constants.DEBUG_SERVICE && orderList.isEmpty()) System.out.println(username+"的订单表为空");
        String token = JwtUtil.generate(username);
        data.put("token",token);
        data.put("orderList",orderList);
        return ReturnPack.success(data);
    }

    //查看具体订单信息
    public ReturnPack viewOrder(HttpServletRequest httpServletRequest, String orderId){
        String username =  httpServletRequest.getHeader("UserName");
//        System.out.println("查看订单时获取的username:"+username);
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        Order order = getOrder(Integer.parseInt(orderId));
        if(Constants.DEBUG_SERVICE)System.out.println("订单属性:"+order.toString());
        String token = JwtUtil.generate(username);
        data.put("order",order);
        return ReturnPack.success(data);
    }

    //生成新订单并加入session
    public ReturnPack generateOrder(HttpServletRequest httpServletRequest, Payment payment,Order order){
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        String username =  httpServletRequest.getHeader("UserName");
        Account account1 = (Account) accountService.getAccount(username);
        account1.setFirstName(order.getBillToFirstName());
        account1.setLastName(order.getBillToLastName());
        account1.setAddress1(order.getBillAddress1());
        account1.setAddress2(order.getBillAddress2());
        account1.setCity(order.getBillCity());
        account1.setState(order.getBillState());
        account1.setZip(order.getBillZip());
        account1.setCountry(order.getBillCountry());
        List<CartItem> cartItemList = cartService.getCartItemListByUsername(account1.getUsername());
        Order order1 = order;
        order1.initOrder(account1,cartItemList,payment);
        order1.setStatus("0");
        data.put("order",order1);
        return ReturnPack.success(data);
    }

    public ReturnPack confirmOrder(HttpServletRequest httpServletRequest,Order order){
        System.out.println("确认订单:"+order);
        JSONObject data;
        if(httpServletRequest.getAttribute("data")!=null) data = (JSONObject) httpServletRequest.getAttribute("data");
        else  {
            data = new JSONObject();
//            String token = JwtUtil.generate(username);
//            data.put("token",token);
        }
        String username =  httpServletRequest.getHeader("UserName");


        int orderId = insertOrder(order);
        data.put("orderId",orderId);
//        session.removeAttribute("order");//移出订单
        cartService.clear(username);//清空购物车
        return ReturnPack.success(data);
    }

    /**
     * @更新订单信息
     * @参数：Order对象
     */
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
        orderMapper.updateOrderStatus(order);
    }

    /**
     * @删除订单
     * @参数：订单id
     */
    public void removeOrder(int orderId){
        orderMapper.removeOrder(orderId);
        orderMapper.removeOrderStatus(orderId);
    }

    /**
     * @发送订单
     * @参数：订单id
     * @第二个参数1表示订单已发送，0表示未发送
     */
    public void sendOrder(int orderId){
        orderMapper.sendOrder(orderId,1);
    }

    /**
     * @获取特定状态的订单、
     * @参数是订单状态，0表示未发送，1表示已发送
     * @返回订单的列表
     */
    public List<Order> getOrdersByStatus(int status){
        List<Order> orderList = orderMapper.getOrdersByStatus(status);
        for(int i=0;i<orderList.size();i++) {
            orderList.get(i).setLineItems(lineItemMapper.getLineItemsByOrderId(orderList.get(i).getOrderId()));
            for(int j=0;j<orderList.get(i).getLineItems().size();j++) {
                LineItem lineItem = (LineItem) orderList.get(i).getLineItems().get(j);
                Item item = itemMapper.getItem(lineItem.getItemId());
                item.setQuantity(orderMapper.getOrderItemQuantity(orderList.get(i).getOrderId(),item.getItemId()));
                lineItem.setItem(item);
            }
        }

        return orderList;
    }

    /**
     * @根据关键字获得订单列表
     * @参数：订单的用户名的关键字
     * @返回订单列表
     */
    public List<Order> searchOrderList(String keyword){
        List<Order> orderList = orderMapper.searchOrderList(keyword);
        for(int i=0;i<orderList.size();i++) {
            orderList.get(i).setLineItems(lineItemMapper.getLineItemsByOrderId(orderList.get(i).getOrderId()));
            for(int j=0;j<orderList.get(i).getLineItems().size();j++) {
                LineItem lineItem = (LineItem) orderList.get(i).getLineItems().get(j);
                Item item = itemMapper.getItem(lineItem.getItemId());
                item.setQuantity(orderMapper.getOrderItemQuantity(orderList.get(i).getOrderId(),item.getItemId()));
                lineItem.setItem(item);
            }
        }

        return orderList;
    }
}
