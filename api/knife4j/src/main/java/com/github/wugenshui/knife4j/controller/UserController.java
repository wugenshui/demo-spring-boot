package com.github.wugenshui.knife4j.controller;

import com.github.wugenshui.knife4j.entity.AjaxResult;
import com.github.wugenshui.knife4j.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author : chenbo
 * @date : 2020-02-08
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
public class UserController {

    @ApiOperation("获取指定用户")
    @GetMapping("{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "用户ID"),
            @ApiImplicitParam(name = "name", value = "用户名称")
    })
    public AjaxResult<User> getUser(@PathVariable Long id, String name) {
        User user = initUser();
        user.setId(id);
        user.setName(name);
        return AjaxResult.success(user);
    }

    @ApiOperation("保存用户")
    @PostMapping
    @ApiParam(name = "user", value = "用户")
    public AjaxResult<User> saveUser(@RequestBody @Validated User user) {
        return AjaxResult.success(user);
    }

    private User initUser() {
        User user = new User();
        user.setId(10086L);
        user.setName("张三");
        user.setAge(18);
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

    @ApiOperation("测试gateway-a")
    @GetMapping("/a")
    public AjaxResult a() {
        return AjaxResult.success("a");
    }

    @ApiOperation("测试gateway-b")
    @GetMapping("/b")
    public AjaxResult b() {
        return AjaxResult.success("b");
    }
}
