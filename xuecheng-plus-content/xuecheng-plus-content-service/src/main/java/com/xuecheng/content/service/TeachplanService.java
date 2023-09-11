package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程计划管理相关接口
 * @create 2023-07-02 18:24
 * @github https://github.com/Ragnarokoo
 */
public interface TeachplanService
{
    /**
     * @author ragnarok
     * @description 根据id查询课程计划
     * @param courseId 课程id
     * @return List<TeachplanDto> 课程计划
     * @date 2023/7/2 18:26
     */
    public List<TeachplanDto> findTeachplayTree(Long courseId);

    /**
     * @author ragnarok
     * @description 新增/修改/保存课程计划
     * @param saveTeachplanDto
     * @return void
     * @date 2023/7/2 20:12
     */
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * @description 教学计划绑定媒资
     * @author Mr.Z
     * @date 2023/7/31 18:20
     * @version 1.0.0
     * @param bindTeachplanMediaDto
     * @return void
     */
    public void associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);
}