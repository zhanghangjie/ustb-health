package com.ustb.mapper;

import com.ustb.pojo.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> findByCondition(Order order);

    void add(Order addOrder);

    Order findById(Integer id);
}
