package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.psi.springboot.pojo.Setmeal;
import com.psi.springboot.pojo.SetmealCheckgroup;
import com.psi.springboot.mappers.SetmealCheckgroupMapper;
import com.psi.springboot.mappers.SetmealMapper;
import com.psi.springboot.service.SetmealService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.PageResult;
import com.psi.springboot.util.QueryPageBean;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealCheckgroupMapper setmealGroupMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Setmeal> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
            queryWrapper.like("name", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("code", queryPageBean.getQueryString());
            queryWrapper.or();
            queryWrapper.like("helpCode", queryPageBean.getQueryString());
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        return new PageResult(setmealPage.getTotal(), setmealPage.getRecords());
    }

    @Override
    @Transactional
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        Result result = new Result();
        try {
            //??????????????????
            QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", setmeal.getCode());
            List setmealist = setmeal.selectList(queryWrapper);
            if (setmealist != null && setmealist.size() > 0) {
                //????????????
                result.setFlag(false);
                result.setMessage(MessageConstant.CHECK_CODE_REPEAT);
                return result;
            }
            //????????????
            setmealMapper.insert(setmeal);
            //????????????????????????
            for (Integer id : checkgroupIds) {
                SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
                setmealCheckgroup.setSetmealId(setmeal.getId());
                setmealCheckgroup.setCheckgroupId(id);
                setmealGroupMapper.insert(setmealCheckgroup);
            }
            result.setFlag(true);
            result.setMessage(MessageConstant.ADD_SETMEAL_SUCCESS);
            //?????????????????????????????????????????????
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();
            setOperations.add(MessageConstant.MYSQL_PIC, setmeal.getImg());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_SETMEAL_FAIL);
            //????????????
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result uploadpic(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //?????????????????????????????????.
        String suffix = originalFilename.substring(lastIndexOf - 1);
        String fileName = UUID.randomUUID() + suffix;
        File file = new File("D:/Upload/YLAssistant/" + fileName);
        try {
            //????????????
            multipartFile.transferTo(file);
            //??????????????????
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }
}
