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
        List<Setmeal> setmealList = setmealMapper.findInfoById(id);
        //获取第一个套餐
        Setmeal firstSetmeal = setmealList.get(0);
        List<Checkgroup> checkgroupList = new ArrayList<>();
        if (setmealList != null) {
            for (Setmeal setmeal : setmealList) {
                //把所有检查组都加到第一个集合里
                setmeal.getCheckgroups().forEach(g -> checkgroupList.add(g));
            }
            firstSetmeal.setCheckgroups(checkgroupList);
            result.setFlag(true);
            result.setData(firstSetmeal);
        } else {
            result.setFlag(false);
        }
        return result;
    }
}
