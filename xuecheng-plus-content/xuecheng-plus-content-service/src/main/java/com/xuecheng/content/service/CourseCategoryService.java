package com.xuecheng.content.service;

import com.xuecheng.api.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-07-01 21:37
 * @github https://github.com/Ragnarokoo
 */
public interface CourseCategoryService
{
    /**
     * @author ragnarok
     * @description 课程分类树形结构查询
     * @param id
     * @return List<CourseCategoryTreeDto>
     * @date 2023/7/1 21:37
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}