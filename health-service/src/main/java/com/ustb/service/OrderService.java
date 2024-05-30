package com.ustb.service;

import com.ustb.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submitOrder(Map map);

    Result findById(Integer id);
}
