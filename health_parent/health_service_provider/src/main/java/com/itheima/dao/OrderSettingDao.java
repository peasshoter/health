package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    OrderSetting findByOrderDate(Date orderDate);
    void editReservationsByOrderDate(OrderSetting orderSetting);
    List<OrderSetting> getOrderSettingByDate(Map<String, Object> map);

}
