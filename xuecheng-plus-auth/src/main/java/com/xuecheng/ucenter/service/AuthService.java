package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 统一认证接口
 * @create 2023-08-31 18:56
 * @github https://github.com/Ragnarokoo
 */
public interface AuthService {

    /**
     * @description 认证方法
     * @author Mr.Z
     * @date 2023/8/31 18:57
     * @param authParamsDto 认证参数
     * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
     */
    XcUserExt execute(AuthParamsDto authParamsDto);
}
