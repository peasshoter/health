package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void setCGroupAndCItem(Map map);//外键

    Page<CheckItem> selectByCondition(String queryString);

    CheckGroup findById(Integer id);
    List<Integer> findItemByGroupId(Integer id);

    void delgroup(Integer id);

    void edit(CheckGroup checkGroup);

    void delAssocication(Integer id);

    List<CheckGroup> findAll();
}
