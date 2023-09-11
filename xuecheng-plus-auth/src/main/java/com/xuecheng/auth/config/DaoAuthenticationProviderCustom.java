package com.xuecheng.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 重写了DaoAuthenticationProvider的校验密码的方法,因为我们统一了认证入口,有一些认证方式不需要校验密码
 * @create 2023-08-31 18:46
 * @github https://github.com/Ragnarokoo
 */
@Component
public class DaoAuthenticationProviderCustom extends DaoAuthenticationProvider {

    @Override
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }
}