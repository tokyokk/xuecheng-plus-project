package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-06-30 20:12
 * @github https://github.com/Ragnarokoo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService
{
    private final CourseBaseMapper courseBaseMapper;
    private final CourseMarketMapper courseMarketMapper;
    private final CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto)
    {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 根据名称模糊查询,在sql中拼接 course_base.name like '%值%'
        queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()), CourseBase::getName,
                courseParamsDto.getCourseName());
        // 根据课程审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()), CourseBase::getAuditStatus,
                courseParamsDto.getAuditStatus());
        //根据课程发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getPublishStatus()), CourseBase::getStatus,
                courseParamsDto.getPublishStatus());

        // 创建page分页对象,参数:当前页码,每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 数据列表
        List<CourseBase> items = pageResult.getRecords();
        // 总记录数
        long total = pageResult.getTotal();

        // List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto)
    {
        /*//合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            //throw new RuntimeException("课程名称为空");
            XueChengPlusException.cast("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }*/

        // 向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        // 将传入的页面的参数放到courseBaseNew对象
        //courseBaseNew.setName(dto.getName());
        // 上面的写法就是从原始对象中get拿到数据向新对象set,比较复杂
        // 主要属性名称一致就可以拷贝
        BeanUtils.copyProperties(dto, courseBaseNew);
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //审核状态设置为未提交
        courseBaseNew.setAuditStatus("202002");
        // 发布状态设置为未发布
        courseBaseNew.setStatus("202001");
        // 插入数据库
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new RuntimeException("添加课程失败");
        }

        // 向课程营销表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        // 将页面输入的数据拷贝到courseMarketNew
        BeanUtils.copyProperties(dto, courseMarketNew);
        // 课程的id
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        // 保存营销信息
        saveCourseMarket(courseMarketNew);
        // 从数据库查询课程的详细信息,包括两部分
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        return courseBaseInfo;
    }

    /**
     * @param courseId
     * @return CourseBaseInfoDto
     * @author ragnarok
     * @description 查询课程信息
     * @date 2023/7/1 23:18
     */
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId)
    {
        // 从课程基本信息表中查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        // 从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        // 组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }
        // 通过courseCategoryMapper查询分类信息,将分类名称放在courseBaseInfoDto对象
        // todo:课程分类的名称设置到courseBaseInfoDto
        return courseBaseInfoDto;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto)
    {
        // 拿到课程id
        Long courseId = editCourseDto.getId();
        // 查询课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }

        // 数据合法性校验
        // 根据具体的业务逻辑去校验
        // 本机构只能修改本机构的课程
        if (!companyId.equals(courseBase.getCompanyId())) {
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        // 封装参数:更新基本信息
        BeanUtils.copyProperties(editCourseDto,courseBase);
        // 修改时间
        courseBase.setChangeDate(LocalDateTime.now());
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if (courseMarket ==null) {
            courseMarket = new CourseMarket();
        }
        BeanUtils.copyProperties(editCourseDto,courseMarket);
        saveCourseMarket(courseMarket);
        // 更新数据库
        int i = courseBaseMapper.updateById(courseBase);
        if (i<=0){
            XueChengPlusException.cast("修改课程失败");
        }
        // 查询课程信息
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        return courseBaseInfo;
    }

    /**
     * @param courseMarketNew
     * @return int
     * @author ragnarok
     * @description 保存营销信息, 逻辑:存在则更新,不存在则添加
     * @date 2023/7/1 23:05
     */
    private int saveCourseMarket(CourseMarket courseMarketNew)
    {
        // 参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge)) {
            throw new RuntimeException("收费规则为空");
        }
        // 如果课程收费,价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0) {
                //throw new RuntimeException("课程的价格不能为空并且必须大于0");
                XueChengPlusException.cast("课程的价格不能为空并且必须大于0");
            }
        }

        /* 从数据库查询营销信息,存在则更新,不存在更新增 */
        /* 主键 */
        Long id = courseMarketNew.getId();
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            // 插入数据库
            int insert = courseMarketMapper.insert(courseMarketNew);
            return insert;
        }
        else {
            // 将courseMarketNew拷贝到courseMarket
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            // 更新
            int i = courseMarketMapper.updateById(courseMarket);
            return i;
        }
    }
}