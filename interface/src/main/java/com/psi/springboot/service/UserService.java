package com.psi.springboot.service;

import com.psi.springboot.util.Result;

public interface UserService {
    //登录验证
    Result login(String username,String password);
}
