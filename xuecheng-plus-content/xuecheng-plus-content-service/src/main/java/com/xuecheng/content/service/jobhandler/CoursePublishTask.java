package com.xuecheng.content.service.jobhandler;

import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.feignclient.SearchServiceClient;
import com.xuecheng.content.mapper.CoursePublishMapper;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.content.service.CoursePublishService;
import com.xuecheng.messagesdk.model.po.MqMessage;
import com.xuecheng.messagesdk.service.MessageProcessAbstract;
import com.xuecheng.messagesdk.service.MqMessageService;
import com.xuecheng.search.po.CourseIndex;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 课程发布任务类
 * @create 2023-08-22 16:22
 * @github https://github.com/Ragnarokoo
 */
@Component
@Slf4j
public class CoursePublishTask extends MessageProcessAbstract {

    @Autowired
    private CoursePublishService coursePublishService;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Autowired
    private CoursePublishMapper coursePublishMapper;

    /**
     * @description 任务调度入口
     * @author Mr.Z
     * @date 2023/8/22 17:55
     */
    @XxlJob("CoursePublishJobHandler")
    public void coursePublishJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex(); // 执行器的序号,从0开始
        int shardTotal = XxlJobHelper.getShardTotal(); // 执行器的总数
        // 调用抽象类的方法执行任务
        process(shardIndex, shardTotal, "course_publish", 30, 60);
    }

    /**
     * @param mqMessage 消息对象
     * @return boolean 任务是否执行成功
     * @description 执行课程发布任务的逻辑,如果抛出异常则证明此任务执行失败
     * @author Mr.Z
     * @date 2023/8/22 16:24
     */
    @Override
    public boolean execute(MqMessage mqMessage) {
        // 从mqMessage拿到课程id
        Long courseId = Long.parseLong(mqMessage.getBusinessKey1());

        // 课程静态化上传到minio
        generateCourseHtml(mqMessage, courseId);

        // 向elasticsearch写索引数据
        saveCourseIndex(mqMessage, courseId);

        // 向redis写缓存

        // 返回true,表示任务完成
        return true;
    }

    /**
     * @param mqMessage 消息对象
     * @param courseId  课程id
     * @return void
     * @description 生成课程静态化页面并上传到文件系统
     * @author Mr.Z
     * @date 2023/8/22 16:28
     */
    private void generateCourseHtml(MqMessage mqMessage, Long courseId) {
        // 消息id
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();

        // 做任务幂等性处理
        // 查询数据库去除该阶段执行状态
        int stageOne = mqMessageService.getStageOne(taskId);
        if (stageOne > 0) {
            log.debug("课程静态化任务完成,无需处理...");
            return;
        }

        // 开始进行课程静态化 生成html页面
        File file = coursePublishService.generateCourseHtml(courseId);
        if (file == null) {
            XueChengPlusException.cast("生成的静态页面为空");
        }

        // 将html页面上传到minio
        coursePublishService.uploadCourseHtml(courseId, file);

        // 任务处理完成写任务状态为完成
        mqMessageService.completedStageOne(taskId);
    }

    /**
     * @param mqMessage 消息对象
     * @param courseId  课程id
     * @return void
     * @description 保存课程索引信息, 第二个阶段任务
     * @author Mr.Z
     * @date 2023/8/22 16:37
     */
    private void saveCourseIndex(MqMessage mqMessage, Long courseId) {
        // 任务id
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();
        // 去除第二个阶段状态
        int stageTwo = mqMessageService.getStageTwo(taskId);

        // 任务幂等性处理
        if (stageTwo > 0) {
            log.debug("课程索引信息已写入,无需执行...");
            return;
        }

        // 查询课程信息,调用索引服务添加索引接口
        // 从课程发布表中查询课程信息
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);

        CourseIndex courseIndex = new CourseIndex();
        BeanUtils.copyProperties(coursePublish,courseIndex);
        // 远程调用
        Boolean add = searchServiceClient.add(courseIndex);
        if (!add) {
            XueChengPlusException.cast("远程调用搜索服务添加索引失败");
        }

        // 完成本阶段的任务
        mqMessageService.completedStageTwo(taskId);
    }
}