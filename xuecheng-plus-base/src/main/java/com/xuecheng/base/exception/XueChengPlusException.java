package com.xuecheng.base.exception;

import lombok.Data;

/**
 * @author ragnarok
 * @version 1.0
 * @description 本项目自定义异常信息
 * @create 2023-07-02 00:30
 * @github https://github.com/Ragnarokoo
 */
@Data
public class XueChengPlusException extends RuntimeException
{
    private String errMessage;

    public XueChengPlusException() {}

    public XueChengPlusException(String message)
    {
        super(message);
        this.errMessage = message;
    }

    public static void cast(String message)
    {
        throw new XueChengPlusException(message);
    }

    public static void cast(CommonError error)
    {
        throw new XueChengPlusException(error.getErrMessage());
    }
}