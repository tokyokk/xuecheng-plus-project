package com.xuecheng.content;

import com.xuecheng.api.dto.CourseCategoryTreeDto;
import com.xuecheng.content.mapper.CourseCategoryMapper;
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
public class CourseCategoryMapperTests
{
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Test
    public void testCourseCategoryMapper()
    {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes("1");
        System.out.println("courseCategoryTreeDtos = " + courseCategoryTreeDtos);
    }
}