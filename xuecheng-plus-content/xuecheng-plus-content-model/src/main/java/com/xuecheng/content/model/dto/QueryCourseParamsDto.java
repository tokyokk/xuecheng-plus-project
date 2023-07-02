package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ragnarok
 * @version 1.0
 * @description 课程查询条件模型类
 * @create 2023-06-29 22:22
 * @github https://github.com/Ragnarokoo
 */
@ApiModel("课程查询条件模型类")
@Data
@ToString
public class QueryCourseParamsDto
{
    @ApiModelProperty("审核状态")
    private String auditStatus;
    @ApiModelProperty("课程名称")
    private String courseName;
    @ApiModelProperty("发布状态")
    private String publishStatus;
}