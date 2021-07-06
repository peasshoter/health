package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
//@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public Result order(Map map) throws Exception {
        String orderDate = (String) map.get("orderDate");
        System.out.println(orderDate);
        Date date = DateUtils.parseString2Date(orderDate, "yyyy-MM-dd");
        System.out.println(date);
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(date);
        System.out.println(byOrderDate.getId());
        if (byOrderDate == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = byOrderDate.getNumber();
        int reservations = byOrderDate.getReservations();
        if (number < reservations) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        String telephone = (String) map.get("telephone");
        Member bytelephone = memberDao.findBytelephone(telephone);
        if (bytelephone != null) {
            Integer id = bytelephone.getId();
            System.out.println(id);
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(id, date, null, null, setmealId);
            List<Order> byCondition = orderDao.findByCondition(order);
            if (byCondition != null && byCondition.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        byOrderDate.setReservations(byOrderDate.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(byOrderDate);
        if (bytelephone == null) {
            bytelephone = new Member();
            bytelephone.setName((String) map.get("name"));
            bytelephone.setPhoneNumber(telephone);
            bytelephone.setIdCard((String) map.get("idCard"));
            bytelephone.setSex((String) map.get("sex"));
            bytelephone.setRegTime(new Date());
            memberDao.add(bytelephone);
        }
        Order order = new Order(bytelephone.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO, Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    @Override
    public Map findBy4Id(int id) throws Exception {

        Map map = orderDao.findBy4Id(id);
        Date date = (Date) map.get("orderDate");
        map.put("orderDate", DateUtils.parseDate2String(date));
        return map;
    }


}
