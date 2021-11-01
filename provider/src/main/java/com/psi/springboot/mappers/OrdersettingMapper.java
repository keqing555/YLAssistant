package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Ordersetting;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface OrdersettingMapper extends BaseMapper<Ordersetting> {
    //根据预约日期查村预约设置（悲观锁）
    Ordersetting selectOrdersettingByOrderDate(String orderDate);

}
