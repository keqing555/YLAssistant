package com.psi.controller.controller;


import com.psi.pojo.Checkgroup;
import com.psi.service.CheckgroupService;
import com.psi.service.CheckitemService;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
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

}

