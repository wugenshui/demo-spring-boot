package com.wugenshui.security.entity;

import lombok.Data;

/**
 * @author : chenbo
 * @date : 2025-05-06
 */
@Data
public class LoginResponseVO {
    /**
     * 令牌
     */
    private String token;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
}
