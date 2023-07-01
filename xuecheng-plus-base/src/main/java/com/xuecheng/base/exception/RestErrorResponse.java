package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author ragnarok
 * @version 1.0
 * @description 和前端约定返回的异常信息模型
 * @create 2023-07-02 00:28
 * @github https://github.com/Ragnarokoo
 */
public class RestErrorResponse implements Serializable
{
    private String errMessage;

    public RestErrorResponse(String errMessage)
    {
        this.errMessage = errMessage;
    }

    public String getErrMessage()
    {
        return errMessage;
    }

    public void setErrMessage(String errMessage)
    {
        this.errMessage = errMessage;
    }
}