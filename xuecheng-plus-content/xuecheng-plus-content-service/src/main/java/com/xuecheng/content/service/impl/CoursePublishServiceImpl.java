package com.xuecheng.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.base.exception.CommonError;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.mapper.CoursePublishMapper;
import com.xuecheng.content.mapper.CoursePublishPreMapper;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.content.model.po.CoursePublishPre;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CoursePublishService;
import com.xuecheng.content.service.TeachplanService;
import com.xuecheng.messagesdk.model.po.MqMessage;
import com.xuecheng.messagesdk.service.MqMessageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 课程接口发布相关实现
 * @create 2023-08-19 20:16
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Service
public class CoursePublishServiceImpl implements CoursePublishService
{

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private TeachplanService teachplanService;

    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Autowired
    private CoursePublishPreMapper coursePublishPreMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CoursePublishMapper coursePublishMapper;

    @Autowired
    private MqMessageService mqMessageService;

    @Autowired
    private MediaServiceClient mediaServiceClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    @Override

    public CoursePreviewDto getCoursePreviewInfo(Long courseId)
    {
        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        // 课程基本信息,营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        coursePreviewDto.setCourseBase(courseBaseInfo);
        // 课程计划信息
        List<TeachplanDto> teachplayTree = teachplanService.findTeachplayTree(courseId);
        coursePreviewDto.setTeachplans(teachplayTree);

        return coursePreviewDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void commitAudit(Long companyId, Long courseId)
    {

        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        if (courseBaseInfo == null)
        {
            XueChengPlusException.cast("课程找不到");
        }
        // 审核状态
        String auditStatus = courseBaseInfo.getAuditStatus();

        // 如果课程的审核状态为已提交则不允许提交
        if ("202003".equals(auditStatus))
        {
            XueChengPlusException.cast("课程已提交请等待审核");
        }

        // todo:本机构只能提交本机构的课程

        // 课程的图片/计划信息没有提交不允许提交
        String pic = courseBaseInfo.getPic();
        if (StringUtils.isEmpty(pic))
        {
            XueChengPlusException.cast("请上传课程图片");
        }

        // 课程计划信息
        List<TeachplanDto> teachplayTree = teachplanService.findTeachplayTree(courseId);
        if (teachplayTree == null || teachplayTree.size() == 0)
        {
            XueChengPlusException.cast("请编写课程计划");
        }

        // 查询课程基本信息/营销信息/计划等信息插入到课程预发布表中
        CoursePublishPre coursePublishPre = new CoursePublishPre();
        BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);
        // 设置机构id
        coursePublishPre.setCompanyId(companyId);
        // 营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 转json
        String courseMarketJson = JSON.toJSONString(courseMarket);
        coursePublishPre.setMarket(courseMarketJson);
        // 计划信息-转json
        String teachplayTreeJson = JSON.toJSONString(teachplayTree);
        coursePublishPre.setTeachplan(teachplayTreeJson);
        // 状态为已提交
        coursePublishPre.setStatus("202003");
        // 提交时间
        coursePublishPre.setCreateDate(LocalDateTime.now());
        // 查询预发布表,如果有记录更新,没有记录则插入
        CoursePublishPre coursePublishPreObj = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPreObj == null)
        {
            // 插入
            coursePublishPreMapper.insert(coursePublishPre);
        } else
        {
            // 更新
            coursePublishPreMapper.updateById(coursePublishPre);
        }

        // 更新课程基本信息表的审核状态为已提交
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setAuditStatus("202003");
        courseBaseMapper.updateById(courseBase);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(Long companyId, Long courseId)
    {
        // 查询预发布表
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre == null)
        {
            XueChengPlusException.cast("课程没有审核记录,无法发布!");
        }
        // 状态
        String status = coursePublishPre.getStatus();
        // 课程如果没有审核通过不允许发布
        if (!"202004".equals(status))
        {
            XueChengPlusException.cast("课程没有审核通过不允许发布");
        }

        // 向课程发布表写入数据
        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(coursePublishPre, coursePublish);
        // 先查询课程发布,如果有则更新,没有再添加
        CoursePublish coursePublishObj = coursePublishMapper.selectById(courseId);
        if (coursePublishObj == null)
        {
            coursePublishMapper.insert(coursePublish);
        } else
        {
            coursePublishMapper.updateById(coursePublish);
        }

        // 向消息表写入数据
        saveCoursePublishMessage(courseId);

        // 将预发布表中数据删除
        coursePublishPreMapper.deleteById(courseId);
    }

    @Override
    public File generateCourseHtml(Long courseId)
    {

        Configuration configuration = new Configuration(Configuration.getVersion());
        // 最终的静态文件
        File htmlFile = null;
        try
        {
            // 拿到classpath路径
//            String classPath = this.getClass().getResource("/").getPath();
            String classPath = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
            // 指定模版的目录
            configuration.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
            // 指定编码
            configuration.setDefaultEncoding("UTF-8");
            // 得到模版
            Template template = configuration.getTemplate("course_template.ftl");
            // 准备数据
            CoursePreviewDto coursePreviewInfo = this.getCoursePreviewInfo(courseId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("model", coursePreviewInfo);

            // Template template 模版, Object model 数据
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            // 输入流
            InputStream inputStream = IOUtils.toInputStream(html, "UTF-8");
            htmlFile = File.createTempFile("coursepublish", ".html");
            // 输出文件
            FileOutputStream outputStream = new FileOutputStream(htmlFile);
            // 使用流将html写入到文件
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception ex)
        {
            log.error("页面静态化出现问题,课程id:{}", courseId, ex);
            ex.printStackTrace();
        }

        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File file)
    {
        try
        {
            // 将file转成MultipartFile
            MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
            // 远程调用
            String upload = mediaServiceClient.upload(multipartFile, "course/" + courseId + ".html");
            if (upload == null)
            {
                log.debug("远程调用走降级的逻辑得到上传的结果为null,课程id:{}", courseId);
                XueChengPlusException.cast("上传静态文件过程中存在异常");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            XueChengPlusException.cast("上传静态文件过程中存在异常");
        }
    }

    /**
     * @param courseId 课程id
     * @return void
     * @description 保存消息表记录
     * @author Mr.Z
     * @date 2023/8/22 15:14
     * @version 1.0.0
     */
    private void saveCoursePublishMessage(Long courseId)
    {
        MqMessage mqMessage = mqMessageService.addMessage("course_publish", String.valueOf(courseId), null, null);
        if (mqMessage == null)
        {
            XueChengPlusException.cast(CommonError.UNKOWN_ERROR);
        }
    }

    /**
     * @param courseId 课程id
     * @return com.xuecheng.content.model.po.CoursePublish
     * @description 根据课程id查询课程信息
     * @author Mr.Z
     * @date 2023/9/1 19:17
     */
    @Override
    public CoursePublish getCoursePublish(Long courseId)
    {
        return coursePublishMapper.selectById(courseId);
    }

    // 解决缓存穿透问题
//    @Override
//    public CoursePublish getCoursePublishCache(Long courseId)
//    {
//        Object jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
//        if (jsonObj != null)
//        {
//            // 缓存中有直接返回数据
//            String jsonString = jsonObj.toString();
//            if ("null".equals(jsonString)) {
//                return null;
//            }
//            CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
//            return coursePublish;
//        } else
//        {
//            // 从数据库查询
//            CoursePublish coursePublish = getCoursePublish(courseId);
////            if (coursePublish != null) {
//                // 查询完成存储到redis
//                redisTemplate.opsForValue().set("course:" + courseId, JSON.toJSONString(coursePublish), 30 + new Random().nextInt(100), TimeUnit.SECONDS);
////            }
//            return coursePublish;
//        }
//    }

    // 使用同步锁解决缓存击穿
//    @Override
//    public CoursePublish getCoursePublishCache(Long courseId)
//    {
//        Object jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
//        if (jsonObj != null)
//        {
//            // 缓存中有直接返回数据
//            String jsonString = jsonObj.toString();
//            if ("null".equals(jsonString))
//            {
//                return null;
//            }
//            CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
//            return coursePublish;
//        } else
//        {
//            synchronized (this)
//            {
//                jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
//                if (jsonObj != null)
//                {
//                    // 缓存中有直接返回数据
//                    String jsonString = jsonObj.toString();
//                    if ("null".equals(jsonString))
//                    {
//                        return null;
//                    }
//                    CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
//                    return coursePublish;
//                }
//
//                // 从数据库查询
//                CoursePublish coursePublish = getCoursePublish(courseId);
////            if (coursePublish != null) {
//                // 查询完成存储到redis
//                redisTemplate.opsForValue().set("course:" + courseId, JSON.toJSONString(coursePublish), 30, TimeUnit.SECONDS);
////            }
//                return coursePublish;
//            }
//        }
//
//    }

    // 使用redis setnx实现分布式锁
//    @Override
//    public CoursePublish getCoursePublishCache(Long courseId)
//    {
//        Object jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
//        if (jsonObj != null)
//        {
//            // 缓存中有直接返回数据
//            String jsonString = jsonObj.toString();
//            if ("null".equals(jsonString))
//            {
//                return null;
//            }
//            CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
//            return coursePublish;
//        } else
//        {
//            // 调用redis的方法,执行setnx命令,谁执行成功谁拿到锁
//            Boolean lock01 = redisTemplate.opsForValue().setIfAbsent("coursequerylock:" + courseId, "01");
//            if (lock01) // true表示设置成功,抢到了锁
//            {
//                jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
//                if (jsonObj != null)
//                {
//                    // 缓存中有直接返回数据
//                    String jsonString = jsonObj.toString();
//                    if ("null".equals(jsonString))
//                    {
//                        return null;
//                    }
//                    CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
//                    return coursePublish;
//                }
//            }
//
//                // 从数据库查询
//                CoursePublish coursePublish = getCoursePublish(courseId);
////            if (coursePublish != null) {
//                // 查询完成存储到redis
//                redisTemplate.opsForValue().set("course:" + courseId, JSON.toJSONString(coursePublish), 30, TimeUnit.SECONDS);
////            }
//
//                return coursePublish;
//
//        }
//    }

    // 使用redisson实现分布式锁
    @Override
    public CoursePublish getCoursePublishCache(Long courseId)
    {
        Object jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
        if (jsonObj != null)
        {
            // 缓存中有直接返回数据
            String jsonString = jsonObj.toString();
            if ("null".equals(jsonString))
            {
                return null;
            }
            CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
            return coursePublish;
        } else
        {
            RLock lock = redissonClient.getLock("coursequerylock:" + courseId);
            // 获取分布式锁
            lock.lock();
            try
            {
                jsonObj = redisTemplate.opsForValue().get("course:" + courseId);
                if (jsonObj != null)
                {
                    // 缓存中有直接返回数据
                    String jsonString = jsonObj.toString();
                    if ("null".equals(jsonString))
                    {
                        return null;
                    }
                    CoursePublish coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
                    return coursePublish;
                }

                // 从数据库查询
                CoursePublish coursePublish = getCoursePublish(courseId);
//            if (coursePublish != null) {
                // 查询完成存储到redis
                redisTemplate.opsForValue().set("course:" + courseId, JSON.toJSONString(coursePublish), 30, TimeUnit.SECONDS);
//            }

                return coursePublish;
            } finally
            {
                lock.unlock();
            }

        }
    }
}