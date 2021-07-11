package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
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
    public void add1(List<OrderSetting> data) {
        if (data != null && data.size() > 0)
            for (OrderSetting orderSetting : data) {
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0) {
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    orderSettingDao.add1(orderSetting);
                }
            }
    }

    @Override
    public void add(Integer[] setmealIds, OrderSetting ordersetting) {
//        orderSettingDao.add(ordersetting);
//        Integer id = ordersetting.getId();
//        for (Integer setmealId : setmealIds) {
//
//        }
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

    // @Override
    // public PageResult findPage(QueryPageBean queryPageBean) {

    //     Integer currentPage = queryPageBean.getCurrentPage();
    //     Integer pageSize = queryPageBean.getPageSize();
    //     String queryString = queryPageBean.getQueryString();
    //     PageHelper.startPage(currentPage, pageSize);

    //     Page<Object> page = orderSettingDao.findPage(queryString);
    //     long total = page.getTotal();
    //     List<Object> result = page.getResult();
    //     return new PageResult(total, result);
    //     //pageresult:{"rows":[{"orderType":"微信预约","phoneNumber":"18511279942","name":"test","orderStatus":"未到诊","orderDate":"2019-04-28 00:00:00","orderAddr":"1"},{"orderType":"微信预约","phoneNumber":"13412345678","name":"王美丽","orderStatus":"未到诊","orderDate":"2021-06-29 00:00:00","orderAddr":"2"},{"orderType":"微信预约","phoneNumber":"13412345678","name":"王美丽","orderStatus":"已到诊","orderDate":"2021-07-03 00:00:00","orderAddr":"3"}],"total":3}玄学第一天第二天结果不同

    // }

    @Override
    public Map<String, Object> findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);

        List<Map<String, Object>> page = orderSettingDao.findPage(queryString);
        PageInfo<Map<String, Object>> pageinfo = new PageInfo<>(page);
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(page);
        System.out.println(pageinfo);
        map.put("total", pageinfo.getTotal());
        map.put("result", page);
        return map;
        //pageresult:{"rows":[{"orderType":"微信预约","phoneNumber":"18511279942","name":"test","orderStatus":"未到诊","orderDate":"2019-04-28 00:00:00","orderAddr":"1"},{"orderType":"微信预约","phoneNumber":"13412345678","name":"王美丽","orderStatus":"未到诊","orderDate":"2021-06-29 00:00:00","orderAddr":"2"},{"orderType":"微信预约","phoneNumber":"13412345678","name":"王美丽","orderStatus":"已到诊","orderDate":"2021-07-03 00:00:00","orderAddr":"3"}],"total":3}玄学第一天第二天结果不同

    }

}
