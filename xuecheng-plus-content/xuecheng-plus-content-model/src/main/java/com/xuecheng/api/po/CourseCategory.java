package com.xuecheng.api.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hmxchen
 * @version 1.0
 * @description TODO
 * @date 2023/1/30 9:58
 */
@ApiModel("课程分类")
@Data
@TableName("course_category")
public class CourseCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键",example="1-1")
    private String id;
    @ApiModelProperty(value = "分类名称",example="前端开发")
    private String name;
    @ApiModelProperty(value = "分类标签",example="前端开发")
    private String label;
    @ApiModelProperty(value = "父结点id",example="1")
    private String parentid;
    @ApiModelProperty(value = "是否显示",example="1")
    private Integer isShow;
    @ApiModelProperty(value = "排序字段",example="1")
    private Integer orderby;
    @ApiModelProperty(value = "是否叶子",example="1")
    private Integer isLeaf;
}
