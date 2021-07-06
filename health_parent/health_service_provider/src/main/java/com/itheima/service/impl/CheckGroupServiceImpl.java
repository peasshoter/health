package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(Integer[] checkitemIds, CheckGroup checkGroup) {
        checkGroupDao.add(checkGroup);
        Integer groupId = checkGroup.getId();

        if (checkitemIds != null && checkitemIds.length > 0) {
            Map<String, Integer> map = null;
            for (Integer checkitemId : checkitemIds) {
                map = new HashMap<>();
                map.put("checkgroup_id", groupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCGroupAndCItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);

    }

    @Override
    public List<Integer> findItemByGroupId(Integer id) {
        return checkGroupDao.findItemByGroupId(id);
    }

    @Override
    public void delgroup(Integer id) {
        checkGroupDao.delgroup(id);
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        checkGroupDao.edit(checkGroup);//修改基础信息
        checkGroupDao.delAssocication(checkGroup.getId());
        Integer groupId = checkGroup.getId();

        if (checkitemIds != null && checkitemIds.length > 0) {
            Map<String, Integer> map = null;
            for (Integer checkitemId : checkitemIds) {
                map = new HashMap<>();
                map.put("checkgroup_id", groupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCGroupAndCItem(map);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
