package com.xuecheng.content;

import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 测试远程调用媒资服务
 * @create 2023-08-22 21:04
 * @github https://github.com/Ragnarokoo
 */
@SpringBootTest
public class FeignUploadTest {

    @Resource
    MediaServiceClient mediaServiceClient;

    @Test
    public void test() throws Exception {

        // 将file转成MultipartFile
        File file = new File("/Users/mac/Downloads/127.html");
        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
        // 远程调用
        String upload = mediaServiceClient.upload(multipartFile, "course/127.html");
        if (upload == null) {
            System.out.println("走了降级逻辑");
        }

    }
}