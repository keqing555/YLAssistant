package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface MenuMapper extends BaseMapper<Menu> {
//根据user_id查询权限菜单
    List<Menu> getMenusByUid(int uid);
}
