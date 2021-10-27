package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psi.springboot.pojo.CheckgroupCheckitem;
import com.psi.springboot.pojo.Checkitem;
import com.psi.springboot.mappers.CheckgroupCheckitemMapper;
import com.psi.springboot.mappers.CheckitemMapper;
import com.psi.springboot.service.CheckitemService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.PageResult;
import com.psi.springboot.util.QueryPageBean;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckitemServiceImpl implements CheckitemService {
    @Autowired
    private CheckitemMapper checkitemMapper;
    @Autowired
    private CheckgroupCheckitemMapper groupItemMapper;

    @Override
    public Result addItem(Checkitem checkitem) {
        Result result = new Result();
        //校验编码唯一
        QueryWrapper<Checkitem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", checkitem.getCode());
        List<Checkitem> list = checkitemMapper.selectList(queryWrapper);
        if (list != null && list.size() > 0) {
            //编码重复
            result.setFlag(false);
            result.setMessage(MessageConstant.CHECK_CODE_REPEAT);
            return result;
        }
        int insert = checkitemMapper.insert(checkitem);
        result.setFlag(true);
        result.setMessage(MessageConstant.ADD_CHECKITEM_SUCCESS);
        return result;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //构建分页对象
        Page<Checkitem> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //构建查询条件对象
        QueryWrapper<Checkitem> queryWrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
            queryWrapper.like("code", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("name", queryPageBean.getQueryString());
        }
        //物理分页查询数据，只查询当前页
        Page<Checkitem> checkitemPage = checkitemMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult(checkitemPage.getTotal(), checkitemPage.getRecords());
        return new PageResult(checkitemPage.getTotal(), checkitemPage.getRecords());
    }

    @Override
    public Result findInfoById(int id) {
        Result result = new Result();
        Checkitem checkitem = checkitemMapper.selectById(id);
        if (checkitem != null) {
            result.setFlag(true);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(checkitem);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }

        return result;
    }

    @Override
    public Result updateInfoById(Checkitem checkitem) {
        Result result = new Result();
        int rows = checkitemMapper.updateById(checkitem);
        if (rows > 0) {
            result.setFlag(true);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_SUCCESS);
            result.setData(checkitem);
        } else {
            result.setFlag(true);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public Result deleteInfoById(int id) {
        Result result = new Result();
        try {
            //删除关系表相关
            Map<String, Object> map = new HashMap<>();
            map.put("checkitem_id", id);
            groupItemMapper.deleteByMap(map);
            //删除检查项
            checkitemMapper.deleteById(id);
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(true);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_FAIL);
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result showAllItemInfo() {
        Result result = new Result();
        List<Checkitem> checkitems = checkitemMapper.selectList(null);
        if (checkitems != null && checkitems.size() > 0) {
            result.setFlag(true);
            result.setData(checkitems);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return result;
    }

    @Override
    public Result getCheckItemIdsByCheckGroupId(int id) {
        Result result = new Result();
        //通过map查询CheckgroupCheckitem
        Map<String, Object> map = new HashMap<>();
        map.put("checkgroup_id", id);
        List<CheckgroupCheckitem> groupItems = groupItemMapper.selectByMap(map);
        if (groupItems != null && groupItems.size() > 0) {
            //获取查到的所有检查项ids
            List<Integer> list = new ArrayList<>();
            for (CheckgroupCheckitem groupItem : groupItems) {
                list.add(groupItem.getCheckitemId());
            }
            result.setFlag(true);
            result.setData(list);
        } else {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return result;
    }
}
