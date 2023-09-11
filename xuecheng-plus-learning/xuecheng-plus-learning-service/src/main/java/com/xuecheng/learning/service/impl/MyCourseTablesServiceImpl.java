package com.xuecheng.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.learning.feignclient.ContentServiceClient;
import com.xuecheng.learning.mapper.XcChooseCourseMapper;
import com.xuecheng.learning.mapper.XcCourseTablesMapper;
import com.xuecheng.learning.model.dto.MyCourseTableParams;
import com.xuecheng.learning.model.dto.XcChooseCourseDto;
import com.xuecheng.learning.model.dto.XcCourseTablesDto;
import com.xuecheng.learning.model.po.XcChooseCourse;
import com.xuecheng.learning.model.po.XcCourseTables;
import com.xuecheng.learning.service.MyCourseTablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mr.Z
 * @description 选课相关的接口实现
 * @create 2023-09-05 20:58
 * @github https://github.com/Ragnarokoo
 */
@Service
@Slf4j
public class MyCourseTablesServiceImpl implements MyCourseTablesService
{
    @Autowired
    private XcChooseCourseMapper chooseCourseMapper;

    @Autowired
    private XcCourseTablesMapper courseTablesMapper;

    @Autowired
    private ContentServiceClient contentServiceClient;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public XcChooseCourseDto addChooseCourse(String userId, Long courseId)
    {
        // 选课调用内容管理服务查询课程的收费规则
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(courseId);

        if (coursepublish == null)
        {
            XueChengPlusException.cast("课程不存在");
        }
        // 收费规则
        String charge = coursepublish.getCharge();
        // 选课记录
        XcChooseCourse xcChooseCourse = null;

        if ("201000".equals(charge))
        { // 免费课程=201000
            // 如果免费课程,向选课记录表
            xcChooseCourse = addFreeCourse(userId, coursepublish);
            // 向我的课程表写数据
            XcCourseTables xcCourseTables = addCourseTables(xcChooseCourse);
        } else
        {
            // 如果课程收费,会向课程记录表写数据
            xcChooseCourse = addChargeCourse(userId, coursepublish);
        }

        // 判断学生的学习资格
        XcCourseTablesDto xcCourseTablesDto = getLearningStatus(userId, courseId);

        // 构造返回值
        XcChooseCourseDto xcChooseCourseDto = new XcChooseCourseDto();
        BeanUtils.copyProperties(xcChooseCourse, xcChooseCourseDto);
        // 设置学习资格
        xcChooseCourseDto.setLearnStatus(xcCourseTablesDto.getLearnStatus());

        return xcChooseCourseDto;
    }

    /**
     * @param userId
     * @param courseId
     * @return com.xuecheng.learning.model.dto.XcCourseTablesDto
     * @description 学习资格，[{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
     * @author Mr.Z
     * @date 2023/9/6 12:24
     */
    @Override
    public XcCourseTablesDto getLearningStatus(String userId, Long courseId)
    {
        // 返回的结果
        XcCourseTablesDto courseTablesDto = new XcCourseTablesDto();
        // 查询我的课程表, 如果查不到说明没有选课
        XcCourseTables xcCourseTables = getXcChooseTables(userId, courseId);
        if (xcCourseTables == null)
        {
            // "code":"702002","desc":"没有选课或选课后没有支付"
            courseTablesDto.setLearnStatus("702002");
            return courseTablesDto;
        }

        // 如果查到了,判断是否过期,如果过期了不能继续学习,没有过期可以继续学习
        boolean before = xcCourseTables.getValidtimeEnd().isBefore(LocalDateTime.now());
        if (before)
        {
            // "code":"702003","desc":"已过期需要申请续期或重新支付"
            BeanUtils.copyProperties(xcCourseTables, courseTablesDto);
            courseTablesDto.setLearnStatus("702003");
            return courseTablesDto;
        } else
        {
            // "code":"702001","desc":"正常学习"
            BeanUtils.copyProperties(xcCourseTables, courseTablesDto);
            courseTablesDto.setLearnStatus("702001");
            return courseTablesDto;
        }
    }

    @Override
    public boolean saveChooseCourseSuccess(final String chooseCourseId)
    {
        // 根据选课id查询选课表
        XcChooseCourse chooseCourse = chooseCourseMapper.selectById(chooseCourseId);
        if (chooseCourse == null) {
            log.debug("接收购买课程的消息,根据选课id在数据库找不到选课记录,选课id:{}", chooseCourseId);
            return false;
        }
        // 选课状态
        final String status = chooseCourse.getStatus();
        // 只有当未支付时,才更新为已支付
        if ("701002".equals(status)) {
            // 更新选课记录状态为支付成功
            chooseCourse.setStatus("701001");
            int i = chooseCourseMapper.updateById(chooseCourse);
            if (i <= 0) {
                log.debug("添加选课记录失败:{}", chooseCourse);
                XueChengPlusException.cast("添加选课记录失败");
            }

            // 向我的课程表插入记录
            addCourseTables(chooseCourse);
            return true;
        }
        return  false;
    }

    @Override
    public PageResult<XcCourseTables> myCourseTables(MyCourseTableParams params)
    {
        // 用户id
        String userId = params.getUserId();
        // 当前页码
        int pageNo = params.getPage();
        // 每页记录数
        int size = params.getSize();

        Page<XcCourseTables> xcCourseTablesPage = new Page<>(pageNo, size);
        LambdaQueryWrapper<XcCourseTables> queryWrapper = new LambdaQueryWrapper<XcCourseTables>().eq(XcCourseTables::getUserId, userId);

        // 查询记录
        Page<XcCourseTables> result = courseTablesMapper.selectPage(xcCourseTablesPage, queryWrapper);

        // 数据列表
        List<XcCourseTables> records = result.getRecords();
        // 总记录数
        long total = result.getTotal();

        // List<T> items, long counts, long page, long pageSize
        PageResult<XcCourseTables> pageResult = new PageResult<>(records, total, pageNo, size);

        return pageResult;
    }

    /**
     * @param userId        用户id
     * @param coursePublish 课程发布表
     * @return com.xuecheng.learning.model.po.XcChooseCourse
     * @description 添加免费课程, 免费课程加入选课记录表, 我的课程表
     * @author Mr.Z
     * @date 2023/9/5 21:09
     */
    public XcChooseCourse addFreeCourse(String userId, CoursePublish coursePublish)
    {
        // 课程id
        Long courseId = coursePublish.getId();
        // 判断,如果是免费的选课记录并且课程状态为成功,直接返回了
        LambdaQueryWrapper<XcChooseCourse> queryWrapper = new LambdaQueryWrapper<XcChooseCourse>().eq(XcChooseCourse::getUserId, userId)
                .eq(XcChooseCourse::getCourseId, courseId)
                .eq(XcChooseCourse::getOrderType, "700001") // 免费课程
                .eq(XcChooseCourse::getStatus, "701001");// 选课成功

        List<XcChooseCourse> xcChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (xcChooseCourses.size() > 0)
        {
            return xcChooseCourses.get(0);
        }

        // 向选课记录表写数据
        XcChooseCourse chooseCourse = new XcChooseCourse();
        chooseCourse.setCourseId(courseId);
        chooseCourse.setCourseName(coursePublish.getName());
        chooseCourse.setUserId(userId);
        chooseCourse.setCompanyId(coursePublish.getCompanyId());
        chooseCourse.setOrderType("700001"); // 免费课程
        chooseCourse.setCreateDate(LocalDateTime.now());
        chooseCourse.setCoursePrice(coursePublish.getPrice());
        chooseCourse.setValidDays(365);
        chooseCourse.setStatus("701001"); // 选课成功
        chooseCourse.setValidtimeStart(LocalDateTime.now()); // 有效期的开始时间
        chooseCourse.setValidtimeEnd(LocalDateTime.now().plusDays(365)); // 有效期的结束时间

        int insert = chooseCourseMapper.insert(chooseCourse);
        if (insert <= 0)
        {
            XueChengPlusException.cast("添加选课记录失败");
        }

        return chooseCourse;
    }

    /**
     * @param xcChooseCourse 选课表
     * @return com.xuecheng.learning.model.po.XcCourseTables
     * @description 添加我的课程表
     * @author Mr.Z
     * @date 2023/9/5 21:09
     */
    public XcCourseTables addCourseTables(XcChooseCourse xcChooseCourse)
    {
        // 选课成功了才可以向我的课程表添加
        String status = xcChooseCourse.getStatus();
        if (!"701001".equals(status))
        {
            XueChengPlusException.cast("选课没有成功无法添加到课程表");
        }

        XcCourseTables xcChooseTables = getXcChooseTables(xcChooseCourse.getUserId(), xcChooseCourse.getCourseId());
        if (xcChooseTables != null)
        {
            return xcChooseTables;
        }

        xcChooseTables = new XcCourseTables();
        BeanUtils.copyProperties(xcChooseCourse, xcChooseTables);
        xcChooseTables.setChooseCourseId(xcChooseCourse.getId()); // 记录选课表的主键
        xcChooseTables.setCourseType(xcChooseCourse.getOrderType()); // 选课类型
        xcChooseTables.setUpdateDate(LocalDateTime.now());
        int insert = courseTablesMapper.insert(xcChooseTables);
        if (insert <= 0)
        {
            XueChengPlusException.cast("添加我的课程表失败");
        }
        return xcChooseTables;
    }

    /**
     * @param userId   用户id
     * @param courseId 课程id
     * @return com.xuecheng.learning.model.po.XcCourseTables
     * @description 根据课程和用户查询我的课程表中某一门课程
     * @author Mr.Z
     * @date 2023/9/6 12:05
     */
    public XcCourseTables getXcChooseTables(String userId, Long courseId)
    {
        return courseTablesMapper.selectOne(new LambdaQueryWrapper<XcCourseTables>()
                .eq(XcCourseTables::getUserId, userId).eq(XcCourseTables::getCourseId, courseId));
    }

    /**
     * @param userId        用户id
     * @param coursePublish 课程发布表
     * @return com.xuecheng.learning.model.po.XcChooseCourse
     * @description 添加收费课程
     * @author Mr.Z
     * @date 2023/9/5 21:09
     */
    public XcChooseCourse addChargeCourse(String userId, CoursePublish coursePublish)
    {
        // 课程id
        Long courseId = coursePublish.getId();
        // 判断,如果是收费的选课记录并且课程状态为待支付,直接返回了
        LambdaQueryWrapper<XcChooseCourse> queryWrapper = new LambdaQueryWrapper<XcChooseCourse>().eq(XcChooseCourse::getUserId, userId)
                .eq(XcChooseCourse::getCourseId, courseId)
                .eq(XcChooseCourse::getOrderType, "700002") // 收费课程
                .eq(XcChooseCourse::getStatus, "701002");// 待支付

        List<XcChooseCourse> xcChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (xcChooseCourses.size() > 0)
        {
            return xcChooseCourses.get(0);
        }

        // 向选课记录表写数据
        XcChooseCourse chooseCourse = new XcChooseCourse();
        chooseCourse.setCourseId(courseId);
        chooseCourse.setCourseName(coursePublish.getName());
        chooseCourse.setUserId(userId);
        chooseCourse.setCompanyId(coursePublish.getCompanyId());
        chooseCourse.setOrderType("700002"); // 收费课程
        chooseCourse.setCreateDate(LocalDateTime.now());
        chooseCourse.setCoursePrice(coursePublish.getPrice());
        chooseCourse.setValidDays(365);
        chooseCourse.setStatus("701002"); // 待支付
        chooseCourse.setValidtimeStart(LocalDateTime.now()); // 有效期的开始时间
        chooseCourse.setValidtimeEnd(LocalDateTime.now().plusDays(365)); // 有效期的结束时间

        int insert = chooseCourseMapper.insert(chooseCourse);
        if (insert <= 0)
        {
            XueChengPlusException.cast("添加选课记录失败");
        }

        return chooseCourse;
    }
}