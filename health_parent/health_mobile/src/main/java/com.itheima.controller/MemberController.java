package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String codeJ = jedisPool.getResource().get(telephone);
        String code = (String) map.get("validateCode");
        if (codeJ == null || !codeJ.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            Member bytelephone = memberService.findBytelephone(telephone);
            if (bytelephone == null) {
                bytelephone = new Member();
                bytelephone.setPhoneNumber(telephone);
                bytelephone.setRegTime(new Date());
                memberService.add(bytelephone);
            }
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
            String bytelephonejson = JSON.toJSON(bytelephone).toString();
            jedisPool.getResource().setex(telephone, 60 * 30, bytelephonejson);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }
}
