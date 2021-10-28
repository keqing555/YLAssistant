package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.mappers.*;
import com.psi.springboot.pojo.Setmeal;
import com.psi.springboot.service.ClientService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (setmealList != null) {
            setmealList.get(0).setCheckgroups(null);
            for (Setmeal setmeal : setmealList) {
                setmealList.get(0).setCheckgroups(setmeal.getCheckgroups());
            }

            result.setFlag(true);
            result.setData(setmealList.get(0));
        } else {
            result.setFlag(false);
        }
        return result;
    }
}
