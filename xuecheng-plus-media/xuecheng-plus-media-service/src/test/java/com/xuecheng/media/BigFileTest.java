package com.xuecheng.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ragnarok
 * @version 1.0
 * @description 测试大文件上传方法
 * @create 2023-07-09 23:54
 * @github https://github.com/Ragnarokoo
 */
public class BigFileTest {
    /**
     * description 分块测试
     * 
     * @author ragnarok
     * @date 2023/7/9 23:55
     * @version 1.0.0 * @param
     * @return void
     */
    @Test
    public void testChunk() throws IOException {
        // 源文件
        File sourceFile = new File("/Users/mac/Downloads/转生王女与令媛千金动漫.mp4");
        // 分块文件存储路径
        String chunkFilePath = "/Users/mac/Downloads/file/";
        // 分块文件大小
        int chunkSize = 1024 * 1024 * 5;
        // 分块文件个数
        int chunkNum = (int)Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        // 使用流从文件读数据,向分块文件写数据
        RandomAccessFile raf_r = new RandomAccessFile(sourceFile, "r");
        // 缓冲区
        byte[] bytes = new byte[1024];
        for (int i = 0; i < chunkNum; i++) {
            File chunkFile = new File(chunkFilePath + i);
            // 分块写入流
            RandomAccessFile raf_rw = new RandomAccessFile(chunkFile, "rw");
            int length = -1;

            while ((length = raf_r.read(bytes)) != -1) {
                raf_rw.write(bytes, 0, length);
                if (chunkFile.length() >= chunkSize) {
                    break;
                }
            }
            raf_rw.close();
        }
        raf_r.close();
    }

    /**
     * description 将分块合并测试
     * 
     * @author ragnarok
     * @date 2023/7/9 23:56
     * @version 1.0.0
     * @param
     * @return void
     */
    @Test
    public void testMerge() throws IOException {
        // 块文件目录
        File chunkFolder = new File("/Users/mac/Downloads/file/");
        // 源文件
        File sourceFile = new File("/Users/mac/Downloads/转生王女与令媛千金动漫.mp4");
        // 合并后的文件
        File mergedFile = new File("/Users/mac/Downloads/转生王女与令媛千金动漫_2.mp4");

        // 取出所有分块文件
        File[] files = chunkFolder.listFiles();
        // 将数组转换为List
        List<File> filesList = Arrays.asList(files);
        // 对分块文件排序
        Collections.sort(filesList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });
        // 向合并文件写的流
        RandomAccessFile raf_rw = new RandomAccessFile(mergedFile, "rw");
        // 缓存区
        byte[] bytes = new byte[1024];
        // 遍历分块文件,向合并的文件写
        for (File file : filesList) {
            // 读分块的流
            RandomAccessFile raf_r = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = raf_r.read(bytes)) != -1) {
                raf_rw.write(bytes, 0, len);
            }
            raf_r.close();
        }
        raf_rw.close();

        // 合并文件完成后对合并的文件md5校验
        FileInputStream fileInputStream_merge = new FileInputStream(mergedFile);
        FileInputStream fileInputStream_source = new FileInputStream(sourceFile);
        String md5_merge = DigestUtils.md5Hex(fileInputStream_merge);
        String md5_source = DigestUtils.md5Hex(fileInputStream_source);
        if (md5_merge.equals(md5_source)) {
            System.out.println("文件合并成功");
        }
    }
}