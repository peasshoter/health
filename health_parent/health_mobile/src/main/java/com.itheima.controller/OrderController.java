package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String codeJ = jedisPool.getResource().get(telephone);
        String code = (String) map.get("validateCode");
        if (codeJ == null || !codeJ.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result =orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()){
            String orderDate= (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(int id) {
       try {
           Map map= orderService.findBy4Id(id);
           return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
       }

    }
}
