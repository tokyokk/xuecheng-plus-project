package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程信息管理接口
 * @create 2023-06-30 20:08
 * @github https://github.com/Ragnarokoo
 */
public interface CourseBaseInfoService
{
    /**
     * @param pageParams      分页查询参数
     * @param companyId       机构id
     * @param courseParamsDto 查询条件
     * @return PageResult<CourseBase> 查询结果
     * @author ragnarok
     * @description 课程分页查询
     * @date 2023/6/30 20:53
     */
    public PageResult<CourseBase> queryCourseBaseList(Long companyId, PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * @author ragnarok
     * @description 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return CourseBaseInfoDto 课程详细信息
     * @date 2023/7/1 22:45
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * @author ragnarok
     * @description 根据课程id查询课程信息
     * @param courseId 课程id
     * @return CourseBaseInfoDto 课程基本信息
     * @date 2023/7/2 16:13
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * @author ragnarok
     * @description 修改课程
     * @param companyId 机构id
     * @param editCourseDto 修改课程信息
     * @return CourseBaseInfoDto 课程详细信息
     * @date 2023/7/2 16:36
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
