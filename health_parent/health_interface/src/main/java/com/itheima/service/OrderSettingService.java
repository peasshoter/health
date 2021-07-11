package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add1(List<OrderSetting> data);

    void add(Integer[] setmealIds, OrderSetting ordersetting);

    List<Map> getOrderSettingByDate(String date);

    void OrderSet(OrderSetting orderSetting);

    Map<String, Object> findPage(QueryPageBean queryPageBean);

}
