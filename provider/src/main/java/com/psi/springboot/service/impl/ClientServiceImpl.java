package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.mappers.*;
import com.psi.springboot.pojo.Checkgroup;
import com.psi.springboot.pojo.Setmeal;
import com.psi.springboot.service.ClientService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private CheckgroupMapper checkgroupMapper;
    @Autowired
    private CheckitemMapper checkitemMapper;

    @Override
    public Result getAllSetmeal() {
        Result result = new Result();
        List<Setmeal> setmealList = setmealMapper.selectList(null);
        if (setmealList != null && setmealList.size() > 0) {
            result.setFlag(true);
            result.setData(setmealList);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return result;
    }

    @Override
    public Result findInfoById(int id) {
        Result result = new Result();
        //获取套餐
        Setmeal setmeal = setmealMapper.selectById(id);
        //获取套餐的检查组
        List<Checkgroup> checkgroupList = checkgroupMapper.findListBySetmealId(id);
        for (Checkgroup checkgroup : checkgroupList) {
            //获取检查组的检查项
            checkgroup.setCheckitems(checkitemMapper.findListByCheckgroupId(checkgroup.getId()));
        }
        //获取套餐的检查组
        setmeal.setCheckgroups(checkgroupList);
        result.setFlag(true);
        result.setData(setmeal);
        return result;
    }
}
