package com.psi.springboot.controller;


import com.psi.springboot.service.UserService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
//@Controller
//@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public Result login(String username, String password, HttpSession session) {
        Result result = userService.login(username, password);
        if (result.isFlag()) {
            //登录成功，储存当前用户
            session.setAttribute("currentUser", result.getData());
        }
        return result;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        try {
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index.html";
    }
}

