package com.psi.service;

import com.psi.pojo.Ordersetting;
import com.psi.util.Result;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    //文件上传
    void uploadTempleate(List<String[]> list);

    //获取每个月份的预约数据
    Result getDayInfoByDate(String date);

    //修改预约信息
    Result updatePersonCount(Ordersetting ordersetting);
}
