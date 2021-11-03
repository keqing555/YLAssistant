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
        String encodedPassword = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        System.out.println("数据库加密后的密码：" + encodedPassword);
        return encodedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String pwd = rawPassword.toString();//前端传来的明文密码
        String s = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        System.out.println("明文密码：" + pwd);
        System.out.println("明文加密密码：" + s);
        //前后端密码相同则验证通过
        boolean equals = encodedPassword.equals(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        return equals;
    }
}
