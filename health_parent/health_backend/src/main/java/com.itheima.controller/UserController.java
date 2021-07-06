package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("getUserName")
    public Result getUserName() {
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
