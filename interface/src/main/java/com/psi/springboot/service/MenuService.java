package com.psi.springboot.service;

import com.psi.springboot.util.Result;

public interface MenuService {
    //获取功能菜单
    Result getMenus(int uid);
}
