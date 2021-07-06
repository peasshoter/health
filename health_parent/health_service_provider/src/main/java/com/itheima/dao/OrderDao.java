package com.itheima.dao;

import com.itheima.entity.Result;
import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);



    Map findBy4Id(int id);


    Integer findthisMonthVisitsNumber(String firstDay4ThisMonth);

    Integer findtodayOrderNumber(String today);

    Integer findtodayVisitsNumber(String today);

    Integer findthisWeekOrderNumber(String thisWeekMonday);

    Integer findthisWeekVisitsNumber(String thisWeekMonday);

    Integer findthisMonthOrderNumber(String firstDay4ThisMonth);

    List<Map> findhotSetmeal();
}
