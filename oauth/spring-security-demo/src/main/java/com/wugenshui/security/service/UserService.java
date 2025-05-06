package com.wugenshui.security.service;

import com.wugenshui.security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : chenbo
 * @date : 2023-10-23
 */
@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SysUser getUserByName(String username) {
        return SysUser.builder()
                .id("100")
                .username(username)
                .password(passwordEncoder.encode("123456"))
                .mobile("12315")
                .build();
    }
}
