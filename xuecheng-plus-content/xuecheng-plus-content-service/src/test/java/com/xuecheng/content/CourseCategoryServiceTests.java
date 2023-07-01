package com.xuecheng.content;

import com.xuecheng.api.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-06-30 17:41
 * @github https://github.com/Ragnarokoo
 */
@SpringBootTest
public class CourseCategoryServiceTests
{
    @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    public void testCourseCategoryService()
    {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println("courseCategoryTreeDtos = " + courseCategoryTreeDtos);
    }
}