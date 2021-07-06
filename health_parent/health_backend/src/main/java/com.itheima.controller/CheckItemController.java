package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference //dubbo查找
    private CheckItemService checkItemService;
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        return pageResult;
    }
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("delete")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    @RequestMapping("findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);//返回chekitem结果
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("finditem")
    public Result finditem() {
        try {
            List<CheckItem> checkItem = checkItemService.finditem();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);//返回chekitem结果
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}