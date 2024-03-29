package com.github.wugenshui.best.oauth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenbo
 * @date : 2020-04-26
 */
@RestController
@RequestMapping
@Slf4j
public class TestOauthController {

    @GetMapping("/f0")
    @PreAuthorize("hasAuthority('f1')")
    public String f0() {
        return getUserName() + "访问资源0";
    }

    @GetMapping("/f1")
    @PreAuthorize("hasAnyAuthority('f1', 'f2')")
    public String f1() {
        return getUserName() + "访问资源1";
    }

    @GetMapping("/f2")
    @PreAuthorize("hasRole('admin')")
    public String f2() {
        return getUserName() + "访问资源2";
    }

    @GetMapping("/f3")
    @PreAuthorize("permitAll()")
    public String f3() {
        return getUserName() + "访问资源3";
    }

    private String getUserName() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("访问接口用户：" + principal);
        if (principal == null) {
            username = "匿名访问者";
        } else if (principal instanceof User) {
            User user = (User) principal;
            username = user.getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
