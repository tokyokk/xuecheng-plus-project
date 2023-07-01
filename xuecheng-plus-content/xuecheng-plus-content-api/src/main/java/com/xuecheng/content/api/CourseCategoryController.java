package com.xuecheng.content.api;

import com.xuecheng.api.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程分类相关接口
 * @create 2023-07-01 20:47
 * @github https://github.com/Ragnarokoo
 */
@RestController
@RequiredArgsConstructor
public class CourseCategoryController
{
    private final CourseCategoryService courseCategoryService;

    @GetMapping("course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes()
    {
        return courseCategoryService.queryTreeNodes("1");
    }
}