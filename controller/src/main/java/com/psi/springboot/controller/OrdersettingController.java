package com.psi.springboot.controller;


import com.psi.springboot.pojo.Ordersetting;
import com.psi.springboot.service.OrderSettingService;
import com.psi.springboot.util.POIUtils;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 获取每个月的预约信息
     *
     * @param date
     * @return
     */
    @RequestMapping("getDayInfoByDate")
    public Result getDayInfoByDate(String date) {
        return orderSettingService.getDayInfoByDate(date);
    }

    /**
     * 修改预约细信息
     *
     * @param ordersetting
     * @return
     */
    @RequestMapping("updatePersonCount")
    public Result updatePersonCount(@RequestBody Ordersetting ordersetting) {
        return orderSettingService.updatePersonCount(ordersetting);
    }

    /**
     * 上传Excel文件
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping("uploadTempleate")
    public Result uploadTempleate(@RequestParam("excelFile") MultipartFile multipartFile) {
        Result result = new Result();
        try {
            //读取excel文件，为储存String[]的List，每一条都是一行数据
            List<String[]> excel = POIUtils.readExcel(multipartFile);
            orderSettingService.uploadTempleate(excel);
            result.setFlag(true);
            result.setMessage(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return result;
    }
}


