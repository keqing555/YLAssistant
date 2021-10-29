package com.psi.springboot.controller;

import com.psi.springboot.service.ReportService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取统计分析
     *
     * @return
     */
    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        return reportService.getBusinessReportData();
    }
}
