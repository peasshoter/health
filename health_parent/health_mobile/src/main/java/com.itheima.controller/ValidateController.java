package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {

            System.out.println(code);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            jedisPool.getResource().setex(telephone, 60, code.toString());
            return new Result(false, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            jedisPool.getResource().setex(telephone, 60, code.toString());
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }


    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {

            System.out.println(code);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            jedisPool.getResource().setex(telephone, 60, code.toString());
            return new Result(false, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            jedisPool.getResource().setex(telephone, 60, code.toString());
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }


    }
}
