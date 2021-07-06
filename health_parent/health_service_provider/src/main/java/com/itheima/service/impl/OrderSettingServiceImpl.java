package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.CheckGroupService;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> data) {
        if (data != null && data.size() > 0)
            for (OrderSetting orderSetting : data) {
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0) {
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
    }

    @Override
    public List<Map> getOrderSettingByDate(String date) {
        String begin = date + "/1";
        String end = date + "/31";
        Map<String, Object> mapdate = new HashMap<>();
        mapdate.put("begin", begin);
        mapdate.put("end", end);

        List<OrderSetting> mapList = orderSettingDao.getOrderSettingByDate(mapdate);

        List<Map> result = new ArrayList<>();

        for (OrderSetting orderSetting : mapList) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            result.add(map);
        }
        return result;
    }

    @Override
    public void OrderSet(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count>0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }

}
