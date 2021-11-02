package com.psi.springboot.controller;


import com.psi.springboot.pojo.User;
import com.psi.springboot.service.MenuService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @RequestMapping("getMenus")
    public Result getMenus(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            return menuService.getMenus(currentUser.getId());
        }
        return null;
    }
}

