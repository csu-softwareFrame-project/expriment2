package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    Order getOrder(int orderId);

    int getOrderItemQuantity(@Param("orderId") int orderId, @Param("itemId") String itemId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);

    void updateOrder(Order order);

    void updateOrderStatus(Order order);

    void removeOrder(int orderId);

    void removeOrderStatus(int orderId);

    void sendOrder(@Param("orderId") int orderId,@Param("status") int status);

    List<Order> getOrdersByStatus(int status);
}
