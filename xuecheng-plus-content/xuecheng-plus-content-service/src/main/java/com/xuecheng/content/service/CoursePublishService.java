package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.po.CoursePublish;

import java.io.File;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 课程发布相关的接口
 * @create 2023-08-19 20:14
 * @github https://github.com/Ragnarokoo
 */
public interface CoursePublishService {

    /**
     * @param courseId 课程id
     * @return com.xuecheng.content.model.dto.CoursePreviewDto
     * @description 获取课程预览信息
     * @author Mr.Z
     * @date 2023/8/19 20:15
     * @version 1.0.0
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * @param companyId 机构id
     * @param courseId  课程id
     * @return void
     * @description 提交审核
     * @author Mr.Z
     * @date 2023/8/19 21:38
     * @version 1.0.0
     */
    public void commitAudit(Long companyId, Long courseId);

    /**
     * @description 课程发布
     * @author Mr.Z
     * @date 2023/8/22 13:19
     * @version 1.0.0 
     * @param companyId 机构id
     * @param courseId 课程id
     * @return void 
     */
    public void publish(Long companyId, Long courseId);

    /**
     * @description 课程静态化
     * @author Mr.Z
     * @date 2023/8/27 17:07
     * @version 1.0.0 
     * @param courseId 
     * @return java.io.File 
     */
    public File generateCourseHtml(Long courseId);

    /**
     * @description 上传课程静态化页面
     * @author Mr.Z
     * @date 2023/8/27 17:07
     * @version 1.0.0 
     * @param courseId
     * @param file
     * @return void 
     */
    public void uploadCourseHtml(Long courseId, File file);

    /**
     * @description 根据课程id查询课程信息
     * @author Mr.Z
     * @date 2023/9/1 19:17
     * @param courseId 课程id
     * @return com.xuecheng.content.model.po.CoursePublish
     */
    public CoursePublish getCoursePublish(Long courseId);

    /**
     * @description 根据课程id查询课程信息
     * @author Mr.Z
     * @date 2023/9/1 19:17
     * @param courseId 课程id
     * @return com.xuecheng.content.model.po.CoursePublish
     */
    public CoursePublish getCoursePublishCache(Long courseId);
}