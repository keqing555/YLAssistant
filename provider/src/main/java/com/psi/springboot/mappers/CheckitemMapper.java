package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Checkitem;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface CheckitemMapper extends BaseMapper<Checkitem> {
    //根据检查组id查检查项
    List<Checkitem> findListById(int id);
}
