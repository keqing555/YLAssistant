package com.psi.springboot.service;

import com.psi.springboot.util.Result;

public interface ReportService {
    //获取每月会员统计
    Result getMemberReport();

    //获取套餐统计
    Result getSetmealReport();

    //统计分析
    Result getBusinessReportData();
}
