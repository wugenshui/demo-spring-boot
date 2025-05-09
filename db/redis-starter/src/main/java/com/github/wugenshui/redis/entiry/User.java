package com.github.wugenshui.redis.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : chenbo
 * @date : 2020-05-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -281034684366346160L;
    private Long id;
    private String username;
    private String password;
}
