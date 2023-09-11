package com.xuecheng.content.feignclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-28 19:49
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Component
public class SearchServiceClientFallbackFactory implements FallbackFactory<SearchServiceClient> {
    @Override
    public SearchServiceClient create(Throwable throwable) {
        return courseIndex -> {
            log.error("添加索引发送熔断,索引信息:{},熔断异常:{}",courseIndex,throwable.toString(),throwable);
            return false;
        };
    }
}