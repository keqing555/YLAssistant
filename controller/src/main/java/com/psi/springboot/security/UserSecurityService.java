package com.psi.springboot.security;

import com.psi.springboot.pojo.User;
import com.psi.springboot.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 */
public class UserSecurityService implements UserDetailsService {
    @Reference
    private UserService userService;

    /**
     * 用于获取用户角色信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息和角色信息
        User user = userService.getUserByUsername(username);
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUsername(user.getUsername());
        userSecurity.setPassword(user.getPassword());
        userSecurity.setRoles(user.getRoles());
        return userSecurity;
    }
}
