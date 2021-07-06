package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class poi {
    public static void main(String[] args) throws IOException {
//        try {
//            XSSFWorkbook sheets = new XSSFWorkbook("H:\\开发\\test1.xlsx");
//            XSSFSheet sheet = sheets.getSheetAt(0);
//            for (Row row : sheet) {
//                for (Cell cell : row) {
//                    System.out.println(cell.getStringCellValue());
//                }
//            }
//            sheets.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        XSSFWorkbook sheets = new XSSFWorkbook();
        XSSFSheet test2 = sheets.createSheet("test2");
        XSSFRow row = test2.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("性名");
        row.createCell(2).setCellValue("年龄");
        XSSFRow row1 = test2.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");
        FileOutputStream stream = new FileOutputStream("H:\\开发\\test2.xlsx");
        sheets.write(stream);
        stream.flush();
        stream.close();
        sheets.close();
    }
}
