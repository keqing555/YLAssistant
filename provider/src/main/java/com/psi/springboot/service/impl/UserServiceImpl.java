package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.mappers.UserMapper;
import com.psi.springboot.pojo.User;
import com.psi.springboot.service.UserService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(String username, String password) {
        Result result = new Result();
//        Map<String, Object> map = new HashMap<>();
//        map.put("username", username);
//        map.put("password", password);
//        List<User> users = userMapper.selectByMap(map);
//        if (users != null && users.size() > 0) {
//            result.setFlag(true);
//            result.setMessage(MessageConstant.LOGIN_SUCCESS);
//            result.setData(users.get(0));
//        } else {
//            result.setFlag(false);
//            result.setMessage(MessageConstant.LOGIN_FAIL);
//        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            result.setFlag(true);
            result.setMessage(MessageConstant.LOGIN_SUCCESS);
            result.setData(user);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.LOGIN_FAIL);
        }
        return result;
    }
}
