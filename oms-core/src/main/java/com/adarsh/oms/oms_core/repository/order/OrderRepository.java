package com.adarsh.oms.oms_core.repository.order;

import com.adarsh.oms.oms_core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findBySymbol(String symbol);
    List<Order> findByUserId(Long userId);

}
//
//    public Order save(OrderRequestDto request){
//
//        System.out.println("Order saved to DB");
//        return queryOrder(request);
//    }
//
//    public Order queryOrder(OrderRequestDto request){
//        Order order = new Order();
//
//        order.setSymbol(request.getSymbol());
//        order.setSide(request.getSide());
//        order.setType(request.getType());
//        order.setPrice(request.getPrice());
//        order.setQuantity(request.getQuantity());
//        order.setUserId(request.getUserId());
//
//        return order;
//    }
//}
