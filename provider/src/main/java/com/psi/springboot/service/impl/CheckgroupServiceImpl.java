package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psi.springboot.pojo.Checkgroup;
import com.psi.springboot.pojo.CheckgroupCheckitem;
import com.psi.springboot.mappers.CheckgroupCheckitemMapper;
import com.psi.springboot.mappers.CheckgroupMapper;
import com.psi.springboot.mappers.SetmealCheckgroupMapper;
import com.psi.springboot.service.CheckgroupService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.PageResult;
import com.psi.springboot.util.QueryPageBean;
import com.psi.springboot.util.Result;
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
    @Autowired
    private SetmealCheckgroupMapper setmealGroupMapper;

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
            //?????????????????????
            QueryWrapper<Checkgroup> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", checkgroup.getCode());
            List<Checkgroup> checkgroups = checkgroupMapper.selectList(queryWrapper);
            if (checkgroups != null && checkgroups.size() > 0) {
                result.setFlag(false);
                result.setMessage(MessageConstant.CHECK_CODE_REPEAT);
            } else {
                //???????????????
                checkgroupMapper.insert(checkgroup);
                //???????????????????????????
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
            //????????????
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public Result editCheckgoup(Checkgroup checkgroup, Integer[] checkitemIds) {
        Result result = new Result();
        try {
            //????????????????????????
            Map<String, Object> map = new HashMap<>();
            map.put("checkgroup_id", checkgroup.getId());
            groupItemMapper.deleteByMap(map);
            //???????????????
            checkgroupMapper.updateById(checkgroup);
            //???????????????????????????????????????
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
            //????????????
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result findGroupById(int id) {
        Result result = new Result();
        //???????????????
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
            //???????????????--???????????????????????????
            QueryWrapper<CheckgroupCheckitem> wrapper = new QueryWrapper<>();
            wrapper.eq("checkgroup_id", id);
            groupItemMapper.delete(wrapper);
            //????????????--??????????????????
            Map<String, Object> map = new HashMap<>();
            map.put("checkgroup_id", id);
            setmealGroupMapper.deleteByMap(map);
            //???????????????
            checkgroupMapper.deleteById(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKGROUP_FAIL);
            //????????????
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result getAllGroup() {
        Result result = new Result();
        List<Checkgroup> selectList = checkgroupMapper.selectList(null);
        if (selectList != null && selectList.size() > 0) {
            result.setFlag(true);
            result.setData(selectList);
        } else {
            result.setFlag(true);
            result.setMessage(MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }
}
