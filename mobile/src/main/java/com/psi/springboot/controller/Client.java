package com.psi.springboot.controller;

import com.psi.springboot.service.ClientService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmealcli")
public class Client {
    @Reference
    private ClientService clientService;

    /**
     * 获取所有套餐列表
     *
     * @return
     */
    @RequestMapping("getAllSetmeal")
    public Result getAllSetmeal() {
        return clientService.getAllSetmeal();
    }

    /**
     * 查询套餐详情
     *
     * @return
     */
    @RequestMapping("findInfoById")
    public Result findInfoById(int id) {
        return clientService.findInfoById(id);
    }
}
