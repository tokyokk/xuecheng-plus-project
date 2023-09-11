package com.xuecheng.content.api;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("课程分页查询接口")
    @PreAuthorize("hasAuthority('xc_teachmanager_course_list')") // 指定权限标识符,拥有此权限才可以访问此方法
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto)
    {
        // 当前登录用户
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        // 用户所属机构id
        Long companyId = null;
        if (StringUtils.isNotEmpty(user.getCompanyId())) {
            companyId = Long.parseLong(user.getCompanyId());
        }

        return courseBaseInfoService.queryCourseBaseList(companyId, pageParams, queryCourseParamsDto);
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(value = {ValidationGroups.Inster.class}) AddCourseDto addCourseDto)
    {
        // 查询用户所属机构的id
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }

    @ApiOperation("根据课程id查询课程")
    @GetMapping("course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable("courseId")Long courseId)
    {
        /*// 获取当前用户的身份
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal = " + principal);*/
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        System.out.println("Username = " + user.getUsername());
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated(value = {ValidationGroups.Update.class}) EditCourseDto editCourseDto)
    {
        // 查询用户所属机构的id
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
    }
}