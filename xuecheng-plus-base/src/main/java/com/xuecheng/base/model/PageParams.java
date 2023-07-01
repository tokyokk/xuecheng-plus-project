package com.xuecheng.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ragnarok
 * @version 1.0
 * @description 分页查询参数
 * @create 2023-06-29 22:14
 * @github https://github.com/Ragnarokoo
 */
@ApiModel("分页查询参数")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageParams
{
    @ApiModelProperty("当前页码")
    private Long pageNo = 1L;
    @ApiModelProperty("每页显示记录数")
    private Long pageSize = 30L;
}