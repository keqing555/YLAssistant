package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Order;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface OrderMapper extends BaseMapper<Order> {

    //获取热门套餐
    Map<String,Object> getHotSetmeal();
}
