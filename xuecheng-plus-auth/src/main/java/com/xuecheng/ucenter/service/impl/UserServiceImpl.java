package com.xuecheng.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.ucenter.mapper.XcMenuMapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.model.po.XcMenu;
import com.xuecheng.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-31 17:56
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private XcUserMapper xcUserMapper;

    @Autowired
    private XcMenuMapper xcMenuMapper;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails
     * @description 传入的请求认证参数就是AuthParamsDto
     * @author Mr.Z
     * @date 2023/8/31 18:38
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // 将传入的json转成AuthParamsDto
        AuthParamsDto authParamsDto = null;

        try {
            authParamsDto = JSON.parseObject(username, AuthParamsDto.class);
        } catch (Exception e) {
            throw new RuntimeException("请求认证的参数不符合要求");
        }

        // 认证类型,password,wx,sms
        final String authType = authParamsDto.getAuthType();

        // 根据认证类型从spring容器中取出指定的bean
        final String beanName = authType + "_authservice";
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);
        // 调用统一的execute方法完成认证
        XcUserExt xcUserExt = authService.execute(authParamsDto);
        // 封装xcUserExt用户信息为UserDetails

        return getUserPrincipal(xcUserExt);
    }

    /**
     * @description 查询用户信息
     * @author Mr.Z
     * @date 2023/8/31 23:54
     * @param xcUser 用户id,主键
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    public UserDetails getUserPrincipal(XcUserExt xcUser) {
        final String password = xcUser.getPassword();
        // 权限
        String[] authorities = {"test"};
        // 根据用户id查询用户的权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(xcUser.getId());
        if (xcMenus.size() > 0) {
            ArrayList<String> permissions = new ArrayList<>();
            xcMenus.forEach(m -> {
                // 拿到了用户拥有的权限标识符
                permissions.add(m.getCode());
            });
            // 将permissions转成数组
            authorities = permissions.toArray(new String[0]);
        }

        xcUser.setPassword(null);
        // 将用户信息转为JSON
        final String userJson = JSON.toJSONString(xcUser);

        return User.withUsername(userJson).password(password).authorities(authorities).build();
    }
}