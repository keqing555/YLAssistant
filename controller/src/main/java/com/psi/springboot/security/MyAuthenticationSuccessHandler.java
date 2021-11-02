package com.psi.springboot.security;

import com.alibaba.fastjson.JSONObject;
import com.psi.springboot.pojo.User;
import com.psi.springboot.service.UserService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义认证成功处理器
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Reference
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        //登录成功后保存用户信息到session
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);//保存user到session
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result(true, "登录成功");
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
