package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程计划信息模型类
 * @create 2023-07-02 17:19
 * @github https://github.com/Ragnarokoo
 */
@Data
@ToString
public class TeachplanDto extends Teachplan implements Serializable
{
    //课程计划关联的媒资信息
    private TeachplanMedia teachplanMedia;

    //子结点
    private List<TeachplanDto> teachPlanTreeNodes;
}