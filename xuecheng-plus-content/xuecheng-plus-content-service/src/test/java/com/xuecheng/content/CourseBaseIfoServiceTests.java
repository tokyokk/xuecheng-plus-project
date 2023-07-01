package com.xuecheng.content;

import com.xuecheng.api.dto.QueryCourseParamsDto;
import com.xuecheng.api.po.CourseBase;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-06-30 17:41
 * @github https://github.com/Ragnarokoo
 */
@SpringBootTest
public class CourseBaseIfoServiceTests
{
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testCourseBaseIfoService()
    {
        // 查询条件
        QueryCourseParamsDto courseParamsDto = new QueryCourseParamsDto();
        courseParamsDto.setCourseName("java");
        courseParamsDto.setAuditStatus("202004"); // 202004表示课程审核通过
        // 分页查询对象
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(2L);
        pageParams.setPageSize(2L);

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, courseParamsDto);
        System.out.println("courseBasePageResult = " + courseBasePageResult);
    }
}