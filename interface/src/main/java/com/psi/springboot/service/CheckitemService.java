package com.psi.springboot.service;

import com.psi.springboot.pojo.Checkitem;
import com.psi.springboot.util.PageResult;
import com.psi.springboot.util.QueryPageBean;
import com.psi.springboot.util.Result;

/**
 * 检查项接口
 */
public interface CheckitemService {
    //新增检查项
    Result addItem(Checkitem checkitem);

    //分页查询检查项
    PageResult findPage(QueryPageBean queryPageBean);

    //根据id获取检查项信息
    Result findInfoById(int id);

    //修改检查项
    Result updateInfoById(Checkitem checkitem);

    //删除检查项
    Result deleteInfoById(int id);

    //获取所有检查项
    Result showAllItemInfo();

    //根据检查组id获取检查项id
    Result getCheckItemIdsByCheckGroupId(int id);
}
