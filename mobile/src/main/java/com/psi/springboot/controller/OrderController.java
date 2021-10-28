package com.psi.springboot.controller;

import com.psi.springboot.service.OrderService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    /**
     * 提交预约信息
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping("submitOrder")
    public Result submitOrder(@RequestBody Map<String, String> paramsMap) {
        return orderService.submitOrder(paramsMap);

    }

    /**
     * 根据id查预约信息
     *
     * @param id
     * @return
     */
    @RequestMapping("findOrderById")
    public Result findOrderById(int id) {
        return orderService.findOrderById(id);
    }

}
