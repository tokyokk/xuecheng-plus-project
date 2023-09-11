package com.xuecheng.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 课程计划
 * </p>
 *
 * @author ragnarok
 * @since 2023-06-29 09:54:22
 */
@Getter
@Setter
public class Teachplan implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父级Id
     */
    private Long parentid;

    /**
     * 层级，分为1、2、3级
     */
    private Short grade;

    /**
     * 课程类型:1视频、2文档
     */
    private String mediaType;

    /**
     * 开始直播时间
     */
    private LocalDateTime startTime;

    /**
     * 直播结束时间
     */
    private LocalDateTime endTime;

    /**
     * 章节及课程时介绍
     */
    private String description;

    /**
     * 时长，单位时:分:秒
     */
    private String timelength;

    /**
     * 排序字段
     */
    private Integer orderby;

    /**
     * 课程标识
     */
    private Long courseId;

    /**
     * 课程发布标识
     */
    private Long coursePubId;

    /**
     * 状态（1正常  0删除）
     */
    private Integer status;

    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime changeDate;
}
