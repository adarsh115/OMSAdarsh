package com.adarsh.oms.oms_matching_engine.model;

import com.adarsh.oms.oms_matching_engine.enums.OrderSide;
import com.adarsh.oms.oms_matching_engine.enums.OrderType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternalOrder {
    private String orderId;
    private String clientOrderId;
    private String symbol;
    private OrderSide side;
    private OrderType type;
    private BigDecimal price;

    private int quantity;
    private Instant timestamp;



    public void reduceQuantity(int qty){
        this.quantity -= qty;
    }

    public boolean isFilled(){
        return this.quantity <= 0;
    }
}
