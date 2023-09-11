package com.xuecheng.content.feignclient;

import com.xuecheng.search.po.CourseIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-28 19:46
 * @github https://github.com/Ragnarokoo
 */
@FeignClient(value = "search",fallbackFactory = SearchServiceClientFallbackFactory.class)
public interface SearchServiceClient {

    @PostMapping("/search/index/course")
    public Boolean add(@RequestBody CourseIndex courseIndex);
}