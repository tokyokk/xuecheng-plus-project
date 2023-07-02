package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author ragnarok
 * @since 2023-06-29 09:54:22
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * @author ragnarok
     * @description 课程计划查询
     * @param courseId 课程id
     * @return List<TeachplanDto> 课程计划
     * @date 2023/7/2 17:30
     */
    public List<TeachplanDto> selectTreeNodes(Long courseId);
}
