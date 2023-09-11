package com.xuecheng.learning.service;

import com.xuecheng.base.model.RestResponse;

/**
 * @author Mr.Z
 * @description 在线学习相关的接口
 * @create 2023-09-11 16:42
 * @github https://github.com/Ragnarokoo
 */
public interface LearningService
{
    /**
     * @description 获取教学视频
     * @author Mr.Z
     * @date 2023/9/11 16:45
     * @param userId 用户id
     * @param courseId 课程id
     * @param teachplanId 教学计划id
     * @param mediaId 媒资文件id
     * @return com.xuecheng.base.model.RestResponse<java.lang.String>
     */
    public RestResponse<String> getVideo(String userId, Long courseId, Long teachplanId, String mediaId);
}
