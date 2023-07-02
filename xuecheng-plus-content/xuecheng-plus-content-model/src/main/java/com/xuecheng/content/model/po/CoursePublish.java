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
 * 课程发布
 * </p>
 *
 * @author hmxchen
 */
@Data
@ApiModel(value="CoursePublish", description="课程发布")
public class CoursePublish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "机构ID")
    private Long companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "适用人群")
    private String users;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "创建人")
    private String username;

    @ApiModelProperty(value = "大分类")
    private String mt;

    @ApiModelProperty(value = "大分类名称")
    private String mtName;

    @ApiModelProperty(value = "小分类")
    private String st;

    @ApiModelProperty(value = "小分类名称")
    private String stName;

    @ApiModelProperty(value = "课程等级")
    private String grade;

    @ApiModelProperty(value = "教育模式")
    private String teachmode;

    @ApiModelProperty(value = "课程图片")
    private String pic;

    @ApiModelProperty(value = "课程介绍")
    private String description;

    @ApiModelProperty(value = "课程营销信息，json格式")
    private String market;

    @ApiModelProperty(value = "所有课程计划，json格式")
    private String teachplan;

    @ApiModelProperty(value = "教师信息，json格式")
    private String teachers;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "上架时间")
    private LocalDateTime onlineDate;

    @ApiModelProperty(value = "下架时间")
    private LocalDateTime offlineDate;

    @ApiModelProperty(value = "发布状态")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "收费规则，对应数据字典--203")
    private String charge;

    @ApiModelProperty(value = "现价")
    private Float price;

    @ApiModelProperty(value = "原价")
    private Float originalPrice;

    @ApiModelProperty(value = "课程有效期天数")
    private Integer validDays;


}
