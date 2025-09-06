package com.adarsh.oms.oms_core.repository.order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.adarsh.oms.oms_core.entity.Order;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findRecentOrders(int limit) {
        String jpql = "SELECT o FROM Order o ORDER BY o.createdAt DESC";
        return entityManager.createQuery(jpql, Order.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersWithStatus(String status) {
        String jpql = "SELECT o FROM Order o WHERE o.status = :status";
        return entityManager.createQuery(jpql, Order.class)
                .setParameter("status", status)
                .getResultList();
    }

}