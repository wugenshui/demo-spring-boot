package com.wugenshui.security.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.wugenshui.security.entity.AjaxResult;
import com.wugenshui.security.enums.AjaxResultEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author : chenbo
 * @date : 2025-05-06
 */
@Slf4j
@Component
@RequiredArgsConstructor
// 处理认证异常（当用户未登录或token已过期）
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.error("未认证访问：{}, 异常：{}", request.getRequestURI(), authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        AjaxResult<String> result = AjaxResult.error(AjaxResultEnum.UNAUTHORIZED.getMsg());
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}

