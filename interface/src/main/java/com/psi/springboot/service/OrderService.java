package com.psi.springboot.service;

import com.psi.springboot.util.Result;

import java.util.Map;

/**
 * 预约接口
 */
public interface OrderService {
    //提交预约信息
    Result submitOrder(Map<String, String> paramsMap);
    //根据id查预约信息
    Result findOrderById(int id);
}

