package com.xuecheng.content.feignclient;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-22 21:38
 * @github https://github.com/Ragnarokoo
 */
public class MediaServiceClientFallback implements MediaServiceClient{

    @Override
    public String upload(MultipartFile filedata, String objectName) throws Exception {
        return null;
    }
}