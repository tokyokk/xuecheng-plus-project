package com.xuecheng.api.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hmxchen
 */
@ApiModel("课程基本信息")
@Data
@TableName("course_base")
public class CourseBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "机构ID", example = "1")
    private Long companyId;
    @ApiModelProperty(value = "机构名称", example = "黑马")
    private String companyName;
    @ApiModelProperty(value = "课程名称", example = "大数据")
    private String name;
    @ApiModelProperty(value = "适用人群", example = "具有一定的java基础")
    private String users;
    @ApiModelProperty(value = "课程标签", example = "具有一定的java基础")
    private String tags;
    @ApiModelProperty(value = "大分类", example = "")
    private String mt;
    @ApiModelProperty(value = "小分类", example = "")
    private String st;
    @ApiModelProperty(value = "课程等级", example = "")
    private String grade;
    @ApiModelProperty(value = "教育模式", example = "common普通")
    private String teachmode;
    @ApiModelProperty(value = "课程介绍", example = "")
    private String description;
    @ApiModelProperty(value = "课程图片", example = "")
    private String pic;
    @ApiModelProperty(value = "创建时间", example = "2022-01-01 00:00:00")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    @ApiModelProperty(value = "修改时间", example = "2022-01-01 00:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;
    @ApiModelProperty(value = "创建人", example = "")
    private String createPeople;
    @ApiModelProperty(value = "更新人", example = "")
    private String changePeople;
    @ApiModelProperty(value = "审核状态", example = "", notes = "[{\"code\":\"202001\",\"desc\":\"审核未通过\"},{\"code\":\"202002\",\"desc\":\"未提交\"},{\"code\":\"202003\",\"desc\":\"已提交\"},{\"code\":\"202004\",\"desc\":\"审核通过\"}]")
    private String auditStatus;
    @ApiModelProperty(value = "课程发布状态", example = "")
    private String status;
}
