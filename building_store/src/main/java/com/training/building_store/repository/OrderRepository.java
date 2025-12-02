package com.training.building_store.repository;

import com.training.building_store.model.Order;
import com.training.building_store.model.User;
import com.training.building_store.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // all orders for specific user
    List<Order> findByUser(User user);

    // all order by status
    List<Order> findByStatus(OrderStatus status);

}
