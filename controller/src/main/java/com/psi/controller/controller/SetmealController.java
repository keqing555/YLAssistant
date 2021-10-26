package com.psi.controller.controller;


import com.psi.pojo.Setmeal;
import com.psi.service.SetmealService;
import com.psi.util.MessageConstant;
import com.psi.util.PageResult;
import com.psi.util.QueryPageBean;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 上传图片
     * 写在控制层，因为在服务层需要实现序列化
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping("uploadpic")
    public Result uploadpic(@RequestParam("imgFile") MultipartFile multipartFile) {
        //  return setmealService.uploadpic(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件后缀，.jpg
        String suffix = originalFilename.substring(lastIndexOf);
        //生成随机名称
        String fileName = UUID.randomUUID() + suffix;
        File file = new File("D:/Upload/YLAssistant/" + fileName);
        try {
            //上传图片
            multipartFile.transferTo(file);
            //在redis里存储图片名称
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();
            setOperations.add(MessageConstant.ALL_PIC, fileName);
            //返回图片名称
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}

