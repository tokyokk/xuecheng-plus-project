package com.xuecheng.content.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/****
 * 课程营销信息
 * @author hmxchen
 * @version 1.0
 * @date 2023/1/30 11:27
 */
@ApiModel("课程营销信息")
@Data
@TableName("course_market")
public class CourseMarket implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程id", example = "")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    @ApiModelProperty(value = "费规则，对应数据字典", example = "")
    private String charge;
    @ApiModelProperty(value = "价格", example = "1")
    private Float price;
    @ApiModelProperty(value = "原价", example = "1")
    private Float originalPrice;
    @ApiModelProperty(value = "咨询qq", example = "")
    private String qq;
    @ApiModelProperty(value = "微信", example = "")
    private String wechat;
    @ApiModelProperty(value = "电话", example = "")
    private String phone;
    @ApiModelProperty(value = "有效期天数", example = "1")
    private Integer validDays;
}

