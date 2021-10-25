package com.psi.controller.controller;


import com.psi.pojo.Setmeal;
import com.psi.service.SetmealService;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.MultipartConfig;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    /**
     * 根据查询条件分页
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);
    }

    /**
     * 添加套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        return setmealService.addSetmeal(setmeal, checkgroupIds);
    }

    @RequestMapping("uploadpic")
    //@MultipartConfig
    public Result uploadpic() {
        return null;
    }
}

