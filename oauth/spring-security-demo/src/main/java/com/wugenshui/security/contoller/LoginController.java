package com.wugenshui.security.contoller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWTUtil;
import com.wugenshui.security.entity.AjaxResult;
import com.wugenshui.security.entity.LoginResponseVO;
import com.wugenshui.security.entity.PasswordLoginVO;
import com.wugenshui.security.entity.SysUserDetail;
import com.wugenshui.security.properties.AppProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : chenbo
 * @date : 2025-05-05
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private AppProperties appProperties;

    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping
    public AjaxResult<LoginResponseVO> login(@RequestBody PasswordLoginVO passwordLoginVO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(passwordLoginVO.getUsername(), passwordLoginVO.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authentication);
            //获取用户信息
            SysUserDetail user = (SysUserDetail) authenticate.getPrincipal();
            // 生成token
            Map<String, Object> params = new HashMap<>();
            params.put("id", user.getId());
            params.put("username", user.getUsername());
            LocalDateTime now = LocalDateTime.now().plus(appProperties.getJwtExpirationTime());
            Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
            params.put("exp", instant.getEpochSecond());
            String token = JWTUtil.createToken(params, appProperties.getJwtSecret().getBytes());
            LoginResponseVO loginResponseVO = new LoginResponseVO();
            loginResponseVO.setId(user.getId());
            loginResponseVO.setUsername(user.getUsername());
            loginResponseVO.setToken(token);
            return AjaxResult.success(loginResponseVO);
        } catch (Exception e) {
            return AjaxResult.error("用户名或密码错误");
        }
    }
}
