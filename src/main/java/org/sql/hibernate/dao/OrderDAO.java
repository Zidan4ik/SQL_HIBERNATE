package org.sql.hibernate.dao;

import org.sql.hibernate.entity.Order;

import java.util.List;

public interface OrderDAO {
    void create(int userId);
    List<Order> getAllByUser(int userId);
    List<Order> getAllOrders();
}
