package com.psi.springboot.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psi.springboot.pojo.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author psi
 * @since 2021-10-24
 */
public interface UserMapper extends BaseMapper<User> {
    //根据用户名获取用户信息和角色信息
    User getUserByUsername(String username);
}
