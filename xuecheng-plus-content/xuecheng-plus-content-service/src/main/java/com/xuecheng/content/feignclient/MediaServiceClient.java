package com.xuecheng.content.feignclient;

import com.xuecheng.content.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 远程调用媒资服务的接口
 * @create 2023-08-22 20:57
 * @github https://github.com/Ragnarokoo
 *
 * 使用fallback定义降级是无法拿到熔断异常, 使用fallbackFactory定义降级可以拿到熔断异常
 */
@FeignClient(value = "media-api",configuration = MultipartSupportConfig.class,fallbackFactory = MediaServiceClientFallbackFactory.class)
public interface MediaServiceClient {

    @RequestMapping(value = "/media/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("filedata") MultipartFile filedata,
                         @RequestParam(value = "objectName", required = false) String objectName) throws Exception;
}