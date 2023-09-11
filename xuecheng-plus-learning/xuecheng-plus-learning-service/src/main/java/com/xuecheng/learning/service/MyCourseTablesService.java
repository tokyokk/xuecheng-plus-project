package com.xuecheng.learning.service;

import com.xuecheng.base.model.PageResult;
import com.xuecheng.learning.model.dto.MyCourseTableParams;
import com.xuecheng.learning.model.dto.XcChooseCourseDto;
import com.xuecheng.learning.model.dto.XcCourseTablesDto;
import com.xuecheng.learning.model.po.XcCourseTables;

/**
 * @author Mr.Z
 * @description 选课接口
 * @create 2023-09-05 20:56
 * @github https://github.com/Ragnarokoo
 */
public interface MyCourseTablesService
{
    /**
     * @description 添加课程
     * @author Mr.Z
     * @date 2023/9/5 20:57
     * @param userId 用户id
     * @param courseId 课程id
     * @return com.xuecheng.learning.model.dto.XcChooseCourseDto 
     */
    public XcChooseCourseDto addChooseCourse(String userId, Long courseId);

    /**
     * @description 判断学习资格
     * @author Mr.Z
     * @date 2023/9/6 12:19
     * @param userId 用户id
     * @param courseId 课程id
     * @return com.xuecheng.learning.model.dto.XcCourseTablesDto
     * 学习资格，[{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
     */
    public XcCourseTablesDto getLearningStatus(String userId, Long courseId);

    /**
     * @description 保存选课成功状态
     * @author Mr.Z
     * @date 2023/9/7 20:43
     * @param chooseCourseId 
     * @return boolean 
     */
    public boolean saveChooseCourseSuccess(String chooseCourseId);

    /**
     * @description 我的课程表
     * @author Mr.Z
     * @date 2023/9/11 18:13
     * @param params 
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.learning.model.po.XcCourseTables> 
     */
    public PageResult<XcCourseTables> myCourseTables(MyCourseTableParams params);
}
