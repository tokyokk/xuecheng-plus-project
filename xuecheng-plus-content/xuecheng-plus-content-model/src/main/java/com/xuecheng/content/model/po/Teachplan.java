package com.xuecheng.content.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程计划
 * </p>
 *
 * @author hmxchen
 */
@Data
@ApiModel(value="Teachplan", description="课程计划")
public class Teachplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程计划ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课程计划名称")
    private String pname;

    @ApiModelProperty(value = "课程计划父级Id")
    private Long parentid;

    @ApiModelProperty(value = "层级，分为1、2、3级")
    private Integer grade;

    @ApiModelProperty(value = "课程类型:1视频、2文档")
    private String mediaType;

    @ApiModelProperty(value = "开始直播时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "直播结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "章节及课程时介绍")
    private String description;

    @ApiModelProperty(value = "时长，单位时:分:秒")
    private String timelength;

    @ApiModelProperty(value = "排序字段")
    private Integer orderby;

    @ApiModelProperty(value = "课程标识")
    private Long courseId;

    @ApiModelProperty(value = "课程发布标识")
    private Long coursePubId;

    @ApiModelProperty(value = "状态（1正常  0删除）")
    private Integer status;

    @ApiModelProperty(value = "是否支持试学或预览（试看）")
    private String isPreview;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime changeDate;


}
