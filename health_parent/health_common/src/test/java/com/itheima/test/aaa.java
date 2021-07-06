package com.itheima.test;

import com.itheima.utils.DateUtils;
import org.junit.Test;

import java.util.Date;

public class aaa {
    @Test
    public static void main(String[] args) throws Exception {
        Date date1 = new Date();
        String date2 = "2021-06-15";
        System.out.println(date1);
        System.out.println(DateUtils.parseDate2String(date1));

        Date date = DateUtils.parseString2Date(date2);
        System.out.println(date2+date);
        System.out.println(DateUtils.parseDate2String(date));
    }
}
