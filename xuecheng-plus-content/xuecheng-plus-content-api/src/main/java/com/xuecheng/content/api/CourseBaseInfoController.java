package com.xuecheng.content.api;

import com.xuecheng.api.dto.AddCourseDto;
import com.xuecheng.api.dto.CourseBaseInfoDto;
import com.xuecheng.api.dto.QueryCourseParamsDto;
import com.xuecheng.api.po.CourseBase;
import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-06-29 22:30
 * @github https://github.com/Ragnarokoo
 */
@RestController
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RequiredArgsConstructor
public class CourseBaseInfoController
{
    private final CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto)
    {
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(value = {ValidationGroups.Inster.class}) AddCourseDto addCourseDto)
    {
        // 查询用户所属机构的id
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }
}