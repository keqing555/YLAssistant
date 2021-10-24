package com.psi.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.pojo.Checkitem;
import com.psi.provider.mapper.CheckitemMapper;
import com.psi.service.CheckitemService;
import com.psi.util.MessageConstant;
import com.psi.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class CheckitemServiceImpl implements CheckitemService {
    @Autowired
    private CheckitemMapper checkitemMapper;

    @Override
    public Result addItem(Checkitem checkitem) {
        Result result = new Result();
        //校验编码唯一
        QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",checkitem.getCode());
        List<Checkitem> list = checkitemMapper.selectList(queryWrapper);
        if(list!=null&&list.size()>0){
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
}
