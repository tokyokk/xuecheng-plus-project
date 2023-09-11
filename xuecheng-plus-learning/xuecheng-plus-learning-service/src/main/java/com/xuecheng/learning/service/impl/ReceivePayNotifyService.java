package com.xuecheng.learning.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.learning.config.PayNotifyConfig;
import com.xuecheng.learning.service.MyCourseTablesService;
import com.xuecheng.messagesdk.model.po.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Z
 * @description 接收消息通知
 * @create 2023-09-07 20:36
 * @github https://github.com/Ragnarokoo
 */
@Slf4j
@Service
public class ReceivePayNotifyService
{
    @Autowired
    private MyCourseTablesService myCourseTablesService;

    @RabbitListener(queues = PayNotifyConfig.PAYNOTIFY_QUEUE)
    public void receive(Message message, Channel channel)
    {
        try
        {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        // 解析出消息
        String jsonString = new String(message.getBody());
        // 转成对象
        MqMessage mqMessage = JSON.parseObject(jsonString, MqMessage.class);
        // 解析消息的内容
        // 选课id
        String chooseCourseId = mqMessage.getBusinessKey1();
        // 订单类型
        String orderType = mqMessage.getBusinessKey2();
        // 学习中心服务只要购买课程支付订单的结果
        if ("60201".equals(orderType))
        {
            // 根据消息内容,更新选课记录表,向我的课程表插入记录
            boolean b = myCourseTablesService.saveChooseCourseSuccess(chooseCourseId);
            if (!b)
            {
                XueChengPlusException.cast("保存选课记录失败");
            }
        }

    }
}