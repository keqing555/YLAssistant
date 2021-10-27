package com.psi.springboot.controller;


import com.psi.springboot.pojo.Checkgroup;
import com.psi.springboot.service.CheckgroupService;
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
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckgroupService checkgroupService;
//    @Reference
//    private CheckitemService checkitemService;

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkgroupService.findPage(queryPageBean);
    }

    /**
     * 添加检查组
     *
     * @param checkgroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("addGroup")
    public Result addGroup(@RequestBody Checkgroup checkgroup, int[] checkitemIds) {
        return checkgroupService.addGroup(checkgroup, checkitemIds);
    }

    /**
     * 编辑检查组
     *
     * @param checkgroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("editCheckgroup")
    public Result editCheckgroup(@RequestBody Checkgroup checkgroup, Integer[] checkitemIds) {
        return checkgroupService.editCheckgoup(checkgroup, checkitemIds);
    }

    @RequestMapping("findGroupById")
    public Result findGroupById(int id) {
        return checkgroupService.findGroupById(id);
    }

    @RequestMapping("deleteInfoById")
    public Result deleteInfoById(int id) {
        return checkgroupService.deleteInfoById(id);
    }

    @RequestMapping("getAllGroup")
    public Result getAllGroup() {
        return checkgroupService.getAllGroup();
    }
}

