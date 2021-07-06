package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(Integer[]  checkitemIds, CheckGroup checkGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findItemByGroupId(Integer id);

    void delgroup(Integer id);

    void edit(Integer[] checkitemIds, CheckGroup checkGroup);

    List<CheckGroup> findAll();
}
