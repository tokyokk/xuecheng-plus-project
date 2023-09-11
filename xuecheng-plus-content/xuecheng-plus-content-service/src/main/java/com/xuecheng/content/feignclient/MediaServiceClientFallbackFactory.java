package com.xuecheng.content.feignclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-22 21:40
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Component
public class MediaServiceClientFallbackFactory implements FallbackFactory<MediaServiceClient> {
    @Override
    public MediaServiceClient create(Throwable throwable) {
        return new MediaServiceClient() {
            @Override
            public String upload(MultipartFile filedata, String objectName) throws Exception {
                log.debug("远程调用上传文件的接口发生熔断,{}", throwable.toString(), throwable);
                return null;
            }
        };
    }
}