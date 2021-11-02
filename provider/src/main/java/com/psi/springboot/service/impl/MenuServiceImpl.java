package com.psi.springboot.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.psi.springboot.mappers.MenuMapper;
import com.psi.springboot.pojo.Menu;
import com.psi.springboot.service.MenuService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Result getMenus(int uid) {
        Result result = new Result();
        List<Menu> menuList = menuMapper.getMenusByUid(uid);
        JSONArray jsonArray = new JSONArray();
        for (Menu menu : menuList) {
            if (menu.getLevel() == 1) {
                //一级菜单
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("path", menu.getPath());
                jsonObject.put("title", menu.getName());
                jsonObject.put("icon", menu.getIcon());
                JSONArray children = new JSONArray();
                for (Menu menu1 : menuList) {
                    //二级菜单
                    if (menu1.getParentmenuid() == menu.getId()) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("path", menu1.getPath());
                        jsonObject1.put("title", menu1.getName());
                        jsonObject1.put("linkUrl", menu1.getLinkurl());
                        children.add(jsonObject1);
                    }
                }
                jsonObject.put("children", children);
                jsonArray.add(jsonObject);
            }
        }
        result.setFlag(true);
        result.setData(jsonArray);
        return result;
    }
}
