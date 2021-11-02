package com.psi.springboot.security;

import com.psi.springboot.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实现UserSecurity接口
 * 用于封装用户基本信息和角色信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurity implements UserDetails {
    private String username;
    private String password;
    private List<Role> roles;

    /**
     * 用户权限集，默认添加role
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            //spring识别keyword字段格式：ROLE_**
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
        }
        return list;
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        return null;
    }

    /**
     * 用户是否过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 用户是否上锁
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 凭证是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 账户是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
