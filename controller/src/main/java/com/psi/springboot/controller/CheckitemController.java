package com.psi.springboot.controller;


import com.psi.springboot.pojo.Checkitem;
import com.psi.springboot.service.CheckitemService;
import com.psi.springboot.util.PageResult;
import com.psi.springboot.util.QueryPageBean;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {
    @Reference
    private CheckitemService checkitemService;


    /**
     * 新增检查项
     *
     * @param checkitem
     * @return
     */
    @RequestMapping("addItem")
    public Result addItem(@RequestBody Checkitem checkitem) {
        return checkitemService.addItem(checkitem);
    }

    /**
     * 检查项条件分页
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkitemService.findPage(queryPageBean);
    }

    /**
     * 根据id查询检查项信息
     *
     * @param id
     * @return
     */
    @RequestMapping("findInfoById")
    public Result findInfoById(int id) {
        return checkitemService.findInfoById(id);
    }

    /**
     * 编辑检查项
     *
     * @param checkitem
     * @return
     */
    @RequestMapping("updateInfoById")
    public Result updateInfoById(@RequestBody Checkitem checkitem) {
        return checkitemService.updateInfoById(checkitem);
    }

    @RequestMapping("deleteInfoById")
    public Result deleteInfoById(int id) {
        return checkitemService.deleteInfoById(id);
    }

    /**
     * 显示所有检查项信息
     *
     * @return
     */
    @RequestMapping("showAllItemInfo")
    public Result showAllItemInfo() {
        return checkitemService.showAllItemInfo();
    }

    /**
     * 根据检查组id获取检查项id
     *
     * @param id
     * @return
     */
    @RequestMapping("getCheckItemIdsByCheckGroupId")
    public Result getCheckItemIdsByCheckGroupId(int id) {
       return checkitemService.getCheckItemIdsByCheckGroupId(id);
    }
}

