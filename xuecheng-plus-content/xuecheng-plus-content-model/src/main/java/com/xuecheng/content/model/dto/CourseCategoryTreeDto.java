package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程分类传输对象
 * @create 2023-07-01 20:45
 * @github https://github.com/Ragnarokoo
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable
{
    /*
        子节点
     */
    List<CourseCategoryTreeDto> childrenTreeNodes;
}