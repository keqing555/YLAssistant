package com.psi.springboot.service;

import com.psi.springboot.pojo.User;
import com.psi.springboot.util.Result;

public interface UserService {
    //登录验证
    Result login(String username,String password);
    //根据用户名获取用户信息和角色信息
    User getUserByUsername(String username);
}
