package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void add(Setmeal setmeal);

    void setfksetmealcheckgroup(Map<String, Integer> map);

    Page<Setmeal> findpage(String condition);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();

}
