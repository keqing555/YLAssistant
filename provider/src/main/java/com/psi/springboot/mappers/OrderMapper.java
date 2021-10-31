package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface OrderMapper extends BaseMapper<Order> {
    /*
    返回值类型为Map，会有多条数据，用List接收
    */
    //获取热门套餐
    List<Map<String, Object>> getHotSetmeal();
}
