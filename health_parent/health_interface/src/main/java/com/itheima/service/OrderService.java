package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map map) throws Exception;

    Map findBy4Id(int id) throws Exception;
}
