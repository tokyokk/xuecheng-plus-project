package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.po.XcUser;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 微信扫码接入
 * @create 2023-09-01 16:01
 * @github https://github.com/Ragnarokoo
 */
public interface WxAuthService
{
    /**
     * @description 微信扫码认证,申请令牌,鞋带令牌查询用户信息,保存信息到数据库
     * @author Mr.Z
     * @date 2023/9/1 16:03
     * @param code 授权码
     * @return com.xuecheng.ucenter.model.po.XcUser 
     */
    public XcUser wxAuth(String code);
}