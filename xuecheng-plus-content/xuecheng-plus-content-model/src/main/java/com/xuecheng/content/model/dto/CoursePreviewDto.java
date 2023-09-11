package com.xuecheng.content.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 课程预览模型类
 * @create 2023-08-19 20:09
 * @github https://github.com/Ragnarokoo
 */
@Data
public class CoursePreviewDto {

    /**
     * 课程基本信息, 营销信息
     */
    private CourseBaseInfoDto courseBase;

    /**
     * 课程计划信息
     */
    private List<TeachplanDto> teachplans;

    // 课程师资信息...

}