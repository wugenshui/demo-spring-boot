package com.wugenshui.security.config;

import com.wugenshui.security.entity.SysUser;
import com.wugenshui.security.entity.SysUserDetail;
import com.wugenshui.security.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : chenbo
 * @date : 2023-10-23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByName(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("user is not found");
        }
        SysUserDetail sysUserDetail = new SysUserDetail(
                username,
                sysUser.getPassword(),
                // 该参数暂时用不到，无意义，参数随便填
                AuthorityUtils.createAuthorityList("ROLE_" + username, "f1", username));
        sysUserDetail.setId(sysUser.getId());
        return sysUserDetail;
    }
}
