package com.xuecheng.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    /**
     * description 根据分页参数获取待处理任务
     *
     * @param shardTotal 分片总数
     * @param shardIndex 分片序号
     * @param count      任务数
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     * @author ragnarok
     * @date 2023/7/21 20:14
     * @version 1.0.0
     */
    @Select(value = "SELECT * FROM xc_media.media_process t WHERE t.id % #{shardTotal} = #{shardIndex} AND (t.status=1 or t.status=3) AND t.fail_count < 3 LIMIT #{count}")
    public List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);


    /**
     * @description 开始一个任务
     * @author ragnarok
     * @date 2023/7/21 21:07
     * @version 1.0.0 
     * @param id 任务id
     * @return int 更新记录数
     */
    @Update("update xc_media.media_process m set m.status='4' where (m.status='1' or m.status='3') and m.fail_count<3 and m.id=#{id}")
    public int startTask(@Param("id") long id);
}
