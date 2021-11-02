package com.psi.springboot.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

//自定义密码处理器
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        //原始密码加密

        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String pwd = rawPassword.toString();//前端传来的明文密码
        //前后端密码相同则验证通过
        boolean equals = encodedPassword.equals(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        return equals;
    }
}
