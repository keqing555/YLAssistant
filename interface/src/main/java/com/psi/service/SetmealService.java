package com.psi.service;

import com.psi.pojo.Setmeal;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * 套餐接口
 */
public interface SetmealService {
    //根据条件查询分页
    PageResult findPage(QueryPageBean queryPageBean);
    //添加套餐
    Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);
    //上传图片
    Result uploadpic(MultipartFile multipartFile);
}
