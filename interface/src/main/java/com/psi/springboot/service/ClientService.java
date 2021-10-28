package com.psi.springboot.service;

import com.psi.springboot.util.Result;

/**
 * 客户端接口
 */
public interface ClientService {

    //获取所有套餐
    Result getAllSetmeal();

    //查询套餐详情
    Result findInfoById(int id);
}
