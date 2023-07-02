package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author ragnarok
 * @since 2023-06-29 09:54:21
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory>
{
    /**
     * @param id
     * @return List<CourseCategoryTreeDto>
     * @author ragnarok
     * @description 使用递归查询分类
     * @date 2023/7/1 21:16
     */
    public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
