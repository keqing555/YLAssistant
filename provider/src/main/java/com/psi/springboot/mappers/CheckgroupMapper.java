package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Checkgroup;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface CheckgroupMapper extends BaseMapper<Checkgroup> {

    //根据套餐id查检查组
    List<Checkgroup> findListById(int id);
}
