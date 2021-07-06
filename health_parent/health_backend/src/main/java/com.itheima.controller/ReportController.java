package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));

        }
        map.put("months", months);
        List<Integer> memberCount = memberService.findByMonths(months);


        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            List<Map<String, Object>> setmealcount = setmealService.findSetmealCount();
            map.put("setmealCount", setmealcount);
            for (Map<String, Object> setmealname : setmealcount) {
                String name = (String) setmealname.get("name");
                map.put("setmealNames", name);
            }
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();

            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> reportData = reportService.getBusinessReportData();
            String reportDate = (String) reportData.get("reportDate");
            Integer todayNewMember = (Integer) reportData.get("todayNewMember");
            Integer totalMember = (Integer) reportData.get("totalMember");
            Integer thisWeekNewMember = (Integer) reportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) reportData.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) reportData.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer) reportData.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer) reportData.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) reportData.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) reportData.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer) reportData.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) reportData.get("hotSetmeal");

            String template = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File(template)));
            XSSFSheet sheet = sheets.getSheetAt(0);
            sheet.getRow(2).getCell(5).setCellValue(reportDate);
            sheet.getRow(4).getCell(5).setCellValue(todayNewMember);
            sheet.getRow(4).getCell(7).setCellValue(totalMember);
            sheet.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
            sheet.getRow(5).getCell(7).setCellValue(thisMonthNewMember);
            sheet.getRow(7).getCell(5).setCellValue(todayOrderNumber);
            sheet.getRow(7).getCell(7).setCellValue(todayVisitsNumber);
            sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNumber);
            sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitsNumber);
            sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNumber);
            sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);


            int rownum = 12;
            for (Map map : hotSetmeal) {

                String name = (String) map.get("name");
                long setmeal_count = (long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                XSSFRow row = sheet.getRow(rownum++);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            sheets.write(outputStream);
            outputStream.flush();
            outputStream.close();
            sheets.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

}
