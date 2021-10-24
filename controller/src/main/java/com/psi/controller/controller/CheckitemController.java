package com.psi.controller.controller;


import com.psi.pojo.Checkitem;
import com.psi.service.CheckitemService;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {
    @Reference
    private CheckitemService checkitemService;

    @RequestMapping("addItem")
    public Result addItem(@RequestBody Checkitem checkitem) {
        return checkitemService.addItem(checkitem);

    }
}

