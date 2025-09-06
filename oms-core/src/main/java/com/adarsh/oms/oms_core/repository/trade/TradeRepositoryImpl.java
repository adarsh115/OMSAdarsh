package com.adarsh.oms.oms_core.repository.trade;

import com.adarsh.oms.oms_core.entity.Trade;
import com.adarsh.oms.oms_core.repository.order.OrderRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TradeRepositoryImpl implements TradeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trade> findRecentTrades(int limit) {
        String jpql = "SELECT t FROM Trade t ORDER BY t.executionTime DESC";
        TypedQuery<Trade> query = entityManager.createQuery(jpql, Trade.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }


}
