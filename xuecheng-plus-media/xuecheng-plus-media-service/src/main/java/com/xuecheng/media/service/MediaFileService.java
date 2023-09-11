package com.xuecheng.media.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.po.MediaFiles;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService
{

    /**
     * @description 根据媒资id查询文件信息
     * @author Mr.Z
     * @date 2023/8/19 21:05
     * @version 1.0.0 
     * @param mediaId 媒资id
     * @return com.xuecheng.media.model.po.MediaFiles 
     */
    MediaFiles getFileById(String mediaId);

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author Mr.M
     * @date 2022/9/10 8:57
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams,
                                                  QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 文件信息
     * @param localFilePath       文件本地路径
     * @param objectName      如果传入objectName,按照objectName去存储,如果不传就按照年月日目录存储
     * @return UploadFileResultDto
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto,
                                          String localFilePath,String objectName);

    /**
     * description 将上传文件到数据库
     *
     * @param commonId            机构id
     * @param fileMd5             文件加密md5
     * @param uploadFileParamsDto 上传文件传输对象
     * @param bucket              桶
     * @param objectName          对象名
     * @return MediaFiles 媒体文件
     * @author ragnarok
     * @date 2023/7/9 23:30
     * @version 1.0.0
     */
    public MediaFiles addMediaFilesToDb(Long commonId, String fileMd5, UploadFileParamsDto uploadFileParamsDto,
                                        String bucket, String objectName);

    /**
     * @param fileMd5 文件的md5
     * @return com.xuecheng.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查文件是否存在
     * @author Mr.M
     * @date 2022/9/13 15:38
     */
    public RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @param fileMd5    文件的md5
     * @param chunkIndex 分块序号
     * @return com.xuecheng.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查分块是否存在
     * @author Mr.M
     * @date 2022/9/13 15:39
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * @param fileMd5            文件md5
     * @param chunk              分块序号
     * @param localChunkFilePath 分块文件本地路径
     * @return com.xuecheng.base.model.RestResponse
     * @description 上传分块
     * @author Mr.M
     * @date 2022/9/13 15:50
     */
    public RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath);

    /**
     * @param companyId            机*构id
     * @param fileMd5              文*件md5
     * @param chunkTotal           分*块总和
     * @param uploadFileParamsDto 文件信息
     * @return com.xuecheng.base.model.RestResponse
     * @description 合并分块fatkun
     * @author Mr.M
     * @date 2022/9/13 15:56
     */
    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) throws FileNotFoundException;


    /**
     * @description 下载文件
     * @author ragnarok
     * @date 2023/7/22 13:40
     * @version 1.0.0
     * @param bucket 桶
     * @param objectName 对象名称
     * @return java.io.File 下载的文件
     */
    public File downloadFileFromMinIO(String bucket, String objectName);

    /**
     * 将文件上传到minio
     *
     * @param localFilePath 文件路径
     * @param mimeType      媒体类型
     * @param bucket        桶
     * @param objectName    对象名
     * @return
     */
    public boolean addMediaFilesToMinion(String localFilePath, String mimeType, String bucket, String objectName) ;
}
