package com.psi.springboot.service;

import com.psi.springboot.pojo.Ordersetting;
import com.psi.springboot.util.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderSettingService {
    //文件上传
    void uploadTempleate(List<String[]> list);

    //获取每个月份的预约数据
    Result getDayInfoByDate(String date);

    //修改预约信息
    Result updatePersonCount(Ordersetting ordersetting);
}
