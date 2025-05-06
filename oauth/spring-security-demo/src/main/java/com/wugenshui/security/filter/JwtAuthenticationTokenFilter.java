package com.wugenshui.security.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.wugenshui.security.config.UserDetailsServiceImpl;
import com.wugenshui.security.entity.SysUser;
import com.wugenshui.security.properties.AppProperties;
import com.wugenshui.security.service.UserService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author : chenbo
 * @date : 2025-05-05
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private AppProperties appProperties;

    //@Resource
    //private RedisTemplate redisTemplate;

    private static final String USER_PREFIX = "login:";

    @Resource
    private UserService userService;
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
            //throw new DisabledException("未携带访问令牌");
        }
        //解析token
        String userid;
        String username;
        boolean verify = JWTUtil.verify(token, appProperties.getJwtSecret().getBytes());
        if (!verify) {
            throw new DisabledException("token非法");
        }
        try {

            JWT jwt = JWTUtil.parseToken(token);
            userid = (String) jwt.getPayload("id");
            username = (String) jwt.getPayload("username");
        } catch (Exception e) {
            throw new DisabledException("token非法");
        }
        //从redis中获取用户信息
        //String redisKey = USER_PREFIX + userid;
        //JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(redisKey);
        //User user = JSONObject.parseObject(jsonObject.toJSONString(), User.class);
        if (Objects.isNull(userid)) {
            throw new UsernameNotFoundException("用户未登录");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 存入SecurityContextHolder
        // 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
