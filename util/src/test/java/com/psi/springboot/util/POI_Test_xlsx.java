package com.psi.springboot.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POI_Test_xlsx {
    @Test
    public void poi() throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\晚饭-泡面\\IdeaProjects\\YLAssistant\\controller\\src\\main\\resources\\static\\template\\ordersetting_template.xlsx");
        //创建工作簿
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        //获取excel第一张表
        Sheet sheet = workbook.getSheetAt(0);
        //获取第一行
        Row row = sheet.getRow(0);
        //获取所有行数据
        int lastRowNum = sheet.getLastRowNum();
        for (int r = 0; r <= lastRowNum; r++) {
            Row row1 = sheet.getRow(r);
            short lastCellNum = row1.getLastCellNum();
            for (int c = 0; c <= lastCellNum; c++) {
                Cell cell = row1.getCell(c);
                System.out.print(cell);
            }
            System.out.println();
        }
        //获取第一列
        Cell cell = row.getCell(0);
        System.out.println(cell);
    }

    /**
     * 读取excel表格
     *
     * @throws IOException
     * @throws InvalidFormatException
     */
    @Test
    public void poi2() throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\晚饭-泡面\\IdeaProjects\\YLAssistant\\controller\\src\\main\\resources\\static\\template\\ordersetting_template.xlsx");

        Workbook workbook = WorkbookFactory.create(fileInputStream);
        //获取表个数
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int s = 0; s < numberOfSheets; s++) {
            Sheet sheet = workbook.getSheetAt(s);
            //获取表名
            System.out.println("表名：" + sheet.getSheetName());
            //获取行数
            int lastRowNum = sheet.getLastRowNum();
            for (int r = 0; r < lastRowNum; r++) {
                Row row = sheet.getRow(r);
                //获取列数
                short lastCellNum = row.getLastCellNum();
                for (int c = 0; c < lastCellNum; c++) {
                    Cell cell = row.getCell(c);
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
    }

    /**
     * 创建excel
     *
     * @throws IOException
     */
    @Test
    public void poi3() throws IOException {
        //创建空XSSF工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //创建一个sheet
        Sheet sheet = xssfWorkbook.createSheet("第一个表");
        //创建第一行
        Row row = sheet.createRow(0);
        //创建第一列
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("姓名");
        //创建第二列
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("年龄");
        //创建第10行
        for (int r = 1; r <= 10; r++) {
            Row row1 = sheet.createRow(r);
            Cell cell10 = row1.createCell(0);
            Cell cell20 = row1.createCell(1);
            cell10.setCellValue("aaa");
            cell20.setCellValue(r);
        }
        //写出xlsx文件
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\晚饭-泡面\\IdeaProjects\\YLAssistant\\controller\\src\\main\\resources\\static\\template\\test.xlsx");
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
