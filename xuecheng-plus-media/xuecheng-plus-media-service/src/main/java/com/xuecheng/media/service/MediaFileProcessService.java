package com.xuecheng.media.service;

import com.xuecheng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Z
 * @version 1.0
 * @description 任务处理
 * @create 2023-07-21 20:38
 * @github https://github.com/Ragnarokoo
 */
public interface MediaFileProcessService {

    /**
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count      获取记录数
     * @description 获取待处理任务
     * @author Mr.Z
     * @date 2023/7/21 20:40
     * @version 1.0.0
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex,int shardTotal,  int count);

    /**
     * @param id 任务id
     * @return boolean  true:开启任务成功,false:开启任务失败
     * @description 开始一个任务
     * @author Mr.Z
     * @date 2023/7/21 21:09
     * @version 1.0.0
     */
    public boolean startTask(@Param("id") long id);

    /**
     * @param taskId   任务id
     * @param status   任务状态
     * @param fileId   文件id
     * @param url      url
     * @param errorMsg 错误信息
     * @return void
     * @description 保存任务结果
     * @author Mr.M
     * @date 2022/10/15 11:29
     */
    void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg);
}
