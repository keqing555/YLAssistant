package com.psi.service;

import com.psi.pojo.Checkgroup;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;

/**
 * 检查组接口
 */
public interface CheckgroupService {
    //检查组分页
    PageResult findPage(QueryPageBean queryPageBean);

    //新增检查组
    Result addGroup(Checkgroup checkgroup, int[] ids);

    //编辑检查组
    Result editCheckgoup(Checkgroup checkgroup, Integer[] checkitemIds);

    //根据id查找检查组信息
    Result findGroupById(int id);
    //删除检查组
    Result deleteInfoById(int id);
}
