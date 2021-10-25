package com.psi.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psi.pojo.Setmeal;
import com.psi.pojo.SetmealCheckgroup;
import com.psi.provider.mapper.SetmealCheckgroupMapper;
import com.psi.provider.mapper.SetmealMapper;
import com.psi.service.SetmealService;
import com.psi.util.MessageConstant;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealCheckgroupMapper setmealGroupMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Setmeal> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
            queryWrapper.like("name", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("code", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("helpCode", queryPageBean.getQueryString());
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        return new PageResult(setmealPage.getTotal(), setmealPage.getRecords());
    }

    @Override
    @Transactional
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        Result result = new Result();
        try {
            //判断编码唯一
            QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", setmeal.getCode());
            List setmealist = setmeal.selectList(queryWrapper);
            if (setmealist != null && setmealist.size() > 0) {
                //编码重复
                result.setFlag(false);
                result.setMessage(MessageConstant.CHECK_CODE_REPEAT);
                return result;
            }
            //添加套餐
            setmealMapper.insert(setmeal);
            //添加套餐的检查组
            for (Integer id : checkgroupIds) {
                SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
                setmealCheckgroup.setSetmealId(setmeal.getId());
                setmealCheckgroup.setCheckgroupId(id);
                setmealGroupMapper.insert(setmealCheckgroup);
            }
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_SETMEAL_FAIL);
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
