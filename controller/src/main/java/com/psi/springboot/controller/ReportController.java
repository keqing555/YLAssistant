package com.psi.springboot.controller;

import com.psi.springboot.service.ReportService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("report")
public class ReportController {
    @Reference
    private ReportService reportService;

    /**
     * 获取每月会员统计
     *
     * @return
     */
    @RequestMapping("getMemberReport")
    public Result getMemberReport() {
        return reportService.getMemberReport();
    }

    /**
     * 获取套餐统计
     *
     * @return
     */
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {
        return reportService.getSetmealReport();
    }

    /**
     * 获取统计数据
     *
     * @return
     */
    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        return reportService.getBusinessReportData();
    }

    /**
     * 下载统计数据xlsx文件
     *
     * @return
     */
    @RequestMapping("exportBusinessReport")
    public ResponseEntity<byte[]> exportBusinessReport() {
        //获取运营数据
        Map<String, Object> data = (Map<String, Object>) getBusinessReportData().getData();
        //创建XSSF工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = workbook.createSheet("数据分析");
        //常建标题行
        XSSFRow title = sheet.createRow(0);
        //设置标题列
        title.createCell(0).setCellValue("当天日期");
        title.createCell(1).setCellValue("今日新增会员");
        title.createCell(2).setCellValue("本周新增会员");
        title.createCell(3).setCellValue("本月新增会员");
        title.createCell(4).setCellValue("总会员数");
        title.createCell(5).setCellValue("今日预约量");
        title.createCell(6).setCellValue("本周预约量");
        title.createCell(7).setCellValue("本月预约量");
        title.createCell(8).setCellValue("总预约量");
        //创建第二行
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue((String) data.get("reportDate"));
        row2.createCell(1).setCellValue((Integer) data.get("todayNewMember"));
        row2.createCell(2).setCellValue((Integer) data.get("thisWeekNewMember"));
        row2.createCell(3).setCellValue((Integer) data.get("thisMonthNewMember"));
        row2.createCell(4).setCellValue((Integer) data.get("totalMember"));
        row2.createCell(5).setCellValue((Integer) data.get("todayOrderNumber"));
        row2.createCell(6).setCellValue((Integer) data.get("thisWeekOrderNumber"));
        row2.createCell(7).setCellValue((Integer) data.get("thisMonthOrderNumber"));
        row2.createCell(8).setCellValue((Integer) data.get("totalMember"));
        //创建第三行
        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("热门套餐");
        //创建第四行
        XSSFRow row4 = sheet.createRow(3);
        row4.createCell(0).setCellValue("套餐名称");
        row4.createCell(0).setCellValue("套餐订单数量");
        row4.createCell(0).setCellValue("套餐占比");
        //获取热门套餐数据
        List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) data.get("hotSetmeal");
        for (int l = 0; l < hotSetmeal.size(); l++) {
            XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            Map<String, Object> map = hotSetmeal.get(l);
            row.createCell(0).setCellValue((String) map.get("name"));
            row.createCell(1).setCellValue((Long) map.get("setmeal_count"));
            row.createCell(3).setCellValue((String) map.get("proportion"));
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            workbook.write(output);
            //设置响应类型为流类型
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置打开方式为下载方式
            httpHeaders.setContentDispositionFormData("attachment", "BusinessAnalysisData.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(output.toByteArray(), httpHeaders, HttpStatus.OK);
    }
}
