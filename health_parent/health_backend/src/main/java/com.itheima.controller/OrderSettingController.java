package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            List<String[]> list = POIUtils.readExcel(file);
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));
                data.add(orderSetting);
            }
            orderSettingService.add1(data);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/getOrderSettingByDate")
    public Result getOrderSettingByDate(String date) {
        try {
            List<Map> list = orderSettingService.getOrderSettingByDate(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/OrderSet")
    public Result OrderSet(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.OrderSet(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }

    }

    //    @RequestMapping("/findpage")
//    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
//
//        return orderSettingService.findPage(queryPageBean);
//
//    }
    @RequestMapping("/findpage")
    public Map<String, Object> findPage(@RequestBody QueryPageBean queryPageBean) {

        return orderSettingService.findPage(queryPageBean);

    }

    @RequestMapping("/add")
    public Result add(Integer[] setmealIds, @RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.add(setmealIds, orderSetting);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }

    }
}
