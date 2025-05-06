package com.wugenshui.security.entity;

import lombok.Data;

/**
 * @author : chenbo
 * @date : 2025-05-06
 */
@Data
public class PasswordLoginVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
