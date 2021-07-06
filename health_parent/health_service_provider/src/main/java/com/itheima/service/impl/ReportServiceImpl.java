package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetmealDao setmealDao;


    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //reportDate: null,
        //                todayNewMember: 0,
        //                totalMember: 0,
        //                thisWeekNewMember: 0,
        //                thisMonthNewMember: 0,
        //                todayOrderNumber: 0,
        //                todayVisitsNumber: 0,
        //                thisWeekOrderNumber: 0,
        //                thisWeekVisitsNumber: 0,
        //                thisMonthOrderNumber: 0,
        //                thisMonthVisitsNumber: 0,
        Map<String, Object> map = new HashMap<>();
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        Integer totalMember = memberDao.findMemberTotalCount();
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        Integer todayOrderNumber = orderDao.findtodayOrderNumber(today);
        Integer todayVisitsNumber = orderDao.findtodayVisitsNumber(today);
        Integer thisWeekOrderNumber = orderDao.findthisWeekOrderNumber(thisWeekMonday);
        Integer thisWeekVisitsNumber = orderDao.findthisWeekVisitsNumber(thisWeekMonday);
        Integer thisMonthOrderNumber = orderDao.findthisMonthOrderNumber(firstDay4ThisMonth);
        Integer thisMonthVisitsNumber = orderDao.findthisMonthVisitsNumber(firstDay4ThisMonth);


        map.put("reportDate", today);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        List<Map> hotSetmeal = orderDao.findhotSetmeal();
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
