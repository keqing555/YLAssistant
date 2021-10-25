package com.psi.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psi.pojo.Checkgroup;
import com.psi.pojo.CheckgroupCheckitem;
import com.psi.provider.mapper.CheckgroupCheckitemMapper;
import com.psi.provider.mapper.CheckgroupMapper;
import com.psi.provider.mapper.CheckitemMapper;
import com.psi.service.CheckgroupService;
import com.psi.util.MessageConstant;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckgroupServiceImpl implements CheckgroupService {
    @Autowired
    private CheckgroupMapper checkgroupMapper;
    @Autowired
    private CheckgroupCheckitemMapper groupItemMapper;
//    @Autowired
//    private CheckitemMapper checkitemMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Checkgroup> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Checkgroup> queryWrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
            queryWrapper.like("code", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("name", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("helpCode", queryPageBean.getQueryString());
        }
        Page<Checkgroup> checkgroupPage = checkgroupMapper.selectPage(page, queryWrapper);
        return new PageResult(checkgroupPage.getTotal(), checkgroupPage.getRecords());
    }

    @Override
    @Transactional
    public Result addGroup(Checkgroup checkgroup, int[] ids) {
        Result result = new Result();
        try {
            //判断检查组唯一
            QueryWrapper<Checkgroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", checkgroup.getCode());
            List<Checkgroup> checkgroups = checkgroupMapper.selectList(queryWrapper);
            if (checkgroups != null && checkgroups.size() > 0) {
                result.setFlag(false);
                result.setMessage(MessageConstant.CHECK_CODE_REPEAT);
            } else {
                //新增检查组
                checkgroupMapper.insert(checkgroup);
                //给检查组添加检查项
                for (int id : ids) {
                    CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
                    checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                    checkgroupCheckitem.setCheckitemId(id);
                    groupItemMapper.insert(checkgroupCheckitem);
                }
                result.setFlag(true);
                result.setMessage(MessageConstant.ADD_CHECKGROUP_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKGROUP_FAIL);
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result editCheckgoup(Checkgroup checkgroup, Integer[] checkitemIds) {
        Result result = new Result();
        try {
            //删除原来的检查项
            Map<String, Object> map = new HashMap<>();
            map.put("checkgroup_id", checkgroup.getId());
            groupItemMapper.deleteByMap(map);
            //修改检查组
            checkgroupMapper.updateById(checkgroup);
            //给修改后的检查组新增检查项
            for (int id : checkitemIds) {
                CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitem.setCheckitemId(id);
                groupItemMapper.insert(checkgroupCheckitem);
            }
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKGROUP_FAIL);
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result findGroupById(int id) {
        Result result = new Result();
        //查询检查组
        Checkgroup checkgroup = checkgroupMapper.selectById(id);
        if (checkgroup != null) {
            result.setFlag(true);
            result.setData(checkgroup);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public Result deleteInfoById(int id) {
        Result result = new Result();
        try {
            //先删除价差组的检查项（外键约束）
            QueryWrapper<CheckgroupCheckitem> wrapper = new QueryWrapper<>();
            wrapper.eq("checkgroup_id", id);
            groupItemMapper.delete(wrapper);
            //删除检查组
            checkgroupMapper.deleteById(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKGROUP_FAIL);
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
