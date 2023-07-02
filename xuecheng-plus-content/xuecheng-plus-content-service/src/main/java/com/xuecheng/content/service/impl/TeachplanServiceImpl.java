package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程计划管理
 * @create 2023-07-02 18:27
 * @github https://github.com/Ragnarokoo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TeachplanServiceImpl implements TeachplanService
{
    private final TeachplanMapper teachplanMapper;

    @Override
    public List<TeachplanDto> findTeachplayTree(Long courseId)
    {
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseId);
        return teachplanDtos;
    }
    private int getTeachplanCount(Long courseId,Long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(Teachplan::getParentid,parentId).eq(Teachplan::getCourseId,courseId);
        Long count = teachplanMapper.selectCount(queryWrapper);
        return (int) (count + 1);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto)
    {
        // 通过课程计划id判断是新增还是修改
        Long teachplanId = saveTeachplanDto.getId();
        if (teachplanId == null) {
            // 新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            // 确定排序的字段,找到他的同级节点个数,排序字段就是个数加1 , select count(1) from teachplan where course_id = 117 and parent_id=268
            Long parentid = saveTeachplanDto.getParentid();
            Long courseId = saveTeachplanDto.getCourseId();
            int teachplanCount = getTeachplanCount(courseId, parentid);
            teachplan.setOrderby(teachplanCount);

            teachplanMapper.insert(teachplan);
        } else {
            // 修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            // 将参数复制到teachplan
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        }
    }
}