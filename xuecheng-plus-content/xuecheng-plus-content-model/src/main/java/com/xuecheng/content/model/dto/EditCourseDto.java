package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ragnarok
 * @version 1.0
 * @description 修改课程信息实体
 * @create 2023-07-02 16:30
 * @github https://github.com/Ragnarokoo
 */
@Data
@ApiModel("编辑课程基本信息")
public class EditCourseDto extends AddCourseDto
{
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}