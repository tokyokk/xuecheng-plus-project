package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ragnarok
 * @version 1.0
 * @description 测试MinIO的sdk
 * @create 2023-07-08 17:02
 * @github https://github.com/Ragnarokoo
 */
public class MinIOTest {
    MinioClient minioClient =
        MinioClient.builder().endpoint("http://localhost:9000").credentials("minioadmin", "minioadmin").build();

    /**
     * 上传文件
     *
     * @throws IOException
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    @Test
    public void test_upload() throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
        NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 通过扩展名得到媒体资源类型 mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;// 通用mimeType,字节流

        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        // 上传文件的参数信息
        UploadObjectArgs uploadObjectArgs =
            UploadObjectArgs.builder().bucket("testbucket").filename("/Users/mac/Downloads/转生王女与令媛千金动漫.mp4")
                // .object("AVG.mp4") // 对象名,在桶下存储该文件
                .object("test/avg.mp4") // 对象名,放在子目录下
                .contentType(mimeType) // 设置媒体资源类型
                .build();

        // 上传文件
        minioClient.uploadObject(uploadObjectArgs);

    }

    /**
     * 删除文件
     *
     * @throws Exception
     */
    @Test
    public void test_delete() throws Exception {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket("testbucket").object("AVG.mp4").build();
        // 删除文件
        minioClient.removeObject(removeObjectArgs);
    }

    /**
     * 查询文件,从minio中下载
     *
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    @Test
    public void test_getFiles() throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
        NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("test/avg.mp4").build();

        // 查询远程服务器所获取到的一个流对象
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        // 指定输出流
        FileOutputStream outputStream = new FileOutputStream("/Users/mac/Desktop/1a.mp4");
        IOUtils.copy(inputStream, outputStream);

        // 校验文件的完整性,对文件的内容进行md5
        FileInputStream fileInputStream = new FileInputStream("/Users/mac/Downloads/转生王女与令媛千金动漫.mp4");
        String source_md5 = DigestUtils.md5Hex(fileInputStream);// minio中文件的md5
        FileInputStream fileInputStream1 = new FileInputStream("/Users/mac/Desktop/1a.mp4");
        String local_md5 = DigestUtils.md5Hex(fileInputStream1);// 本地的md5值
        if (source_md5.equals(local_md5)) {
            System.out.println("下载成功");
        }
    }

    /**
     * description 将分块上传到minio
     * 
     * @author ragnarok
     * @date 2023/7/10 01:39
     * @version 1.0.0
     * @param
     * @return void
     */
    @Test
    public void uploadChunk() throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
        NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        for (int i = 0; i < 31; i++) {
            // /Users/mac/Downloads/file
            // 上传文件的参数信息
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket("testbucket")
                .filename("/Users/mac/Downloads/file/" + i).object("chunk/" + i) // 对象名,放在子目录下
                .build();

            // 上传文件
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传分块" + i + "成功");
        }
    }

    /**
     * description 调用minio接口合并分块
     * 
     * @author ragnarok
     * @date 2023/7/10 01:43
     * @version 1.0.0
     * @param
     * @return void
     */
    @Test
    public void testMerget() throws Exception {
        // List<ComposeSource> sources = new ArrayList<ComposeSource>();
        // for (int i = 0; i < 152; i++) {
        // // 指定分块文件的信息
        // ComposeSource composeSource = ComposeSource.builder().bucket("testbucket").object("chunk/" + i).build();
        // sources.add(composeSource);
        // }

        List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(31)
            .map(i -> ComposeSource.builder().bucket("testbucket").object("chunk/" + i).build())
            .collect(Collectors.toList());

        // 指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs =
            ComposeObjectArgs.builder().bucket("testbucket").object("merge01.mp4").sources(sources) // 指定源文件
                .build();
        // 合并文件
        // 报错: size 1048576 must be greater than 5242880 ,minio默认的分片文件大小为5M
        minioClient.composeObject(composeObjectArgs);
    }
}