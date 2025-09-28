package com.adarsh.oms.oms_matching_engine;

import com.adarsh.oms.oms_matching_engine.engine.MatchingEngineService;
import com.adarsh.oms.oms_matching_engine.enums.OrderSide;
import com.adarsh.oms.oms_matching_engine.enums.OrderType;
import com.adarsh.oms.oms_matching_engine.model.InternalOrder;
import com.adarsh.oms.oms_matching_engine.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest()
//class OmsMatchingEngineApplicationTests {
//
//	@Test
//	void testLimitOrderMatching() {
//		MatchingEngineService engine = new MatchingEngineService();
//
//		// Add resting BUY order
//		InternalOrder buyOrder = InternalOrder.builder()
//				.orderId("B1")
//				.clientOrderId("CB1")
//				.symbol("AAPL")
//				.side(OrderSide.BUY)
//				.type(OrderType.LIMIT)
//				.price(new BigDecimal("150.00"))
//				.quantity(100)
//				.timestamp(Instant.now().minusSeconds(60))
//				.build();
//
//		engine.processNewOrder(buyOrder);
//
//		// Incoming SELL order that matches
//		InternalOrder sellOrder = InternalOrder.builder()
//				.orderId("S1")
//				.clientOrderId("CS1")
//				.symbol("AAPL")
//				.side(OrderSide.SELL)
//				.type(OrderType.LIMIT)
//				.price(new BigDecimal("149.00"))
//				.quantity(50)
//				.timestamp(Instant.now())
//				.build();
//
//		List<Trade> trades = engine.processNewOrder(sellOrder);
//
//		assertEquals(1, trades.size());
//		Trade trade = trades.get(0);
//		assertEquals("AAPL", trade.getSymbol());
//		assertEquals(new BigDecimal("150.00"), trade.getPrice()); // resting order sets price
//		assertEquals(50, trade.getQuantity());
//		assertEquals("B1", trade.getBuyOrderId());
//		assertEquals("S1", trade.getSellOrderId());
//	}
//
//}






