package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;


public interface CheckItemService {
    void add(CheckItem checkItem);
    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> finditem();
}
