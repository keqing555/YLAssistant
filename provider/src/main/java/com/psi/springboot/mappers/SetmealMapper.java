package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Setmeal;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface SetmealMapper extends BaseMapper<Setmeal> {
     //根据套餐id查询套餐所有信息
     List<Setmeal> findInfoById(int id);
}
