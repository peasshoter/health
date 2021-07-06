package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public interface ReportService {


    Map<String, Object> getBusinessReportData() throws Exception;
}
