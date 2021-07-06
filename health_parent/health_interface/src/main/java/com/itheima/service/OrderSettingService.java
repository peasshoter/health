package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> data);

    List<Map> getOrderSettingByDate(String date);

    void OrderSet(OrderSetting orderSetting);
}
