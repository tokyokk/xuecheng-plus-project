package com.xuecheng.content;

import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.service.CoursePublishService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author ragnarok
 * @version 1.0
 * @description
 * @create 2023-06-30 17:41
 * @github https://github.com/Ragnarokoo
 */
@SpringBootTest
public class FreemarkerTest
{
    @Autowired
    private CoursePublishService coursePublishService;

    @Test
    public void testGenerateHtmlByTemplate() throws IOException, TemplateException {

        Configuration configuration = new Configuration(Configuration.getVersion());

        // 拿到classpath路径
        String classPath = this.getClass().getResource("/").getPath();
        // 指定模版的目录
        configuration.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
        // 指定编码
        configuration.setDefaultEncoding("UTF-8");
        // 得到模版
        Template template = configuration.getTemplate("course_template.ftl");
        // 准备数据
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(127L);
        HashMap<String, Object> map = new HashMap<>();
        map.put("model", coursePreviewInfo);

        // Template template 模版, Object model 数据
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        // 输入流
        InputStream inputStream = IOUtils.toInputStream(html, "UTF-8");
        // 输出文件
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/mac/Downloads/127.html"));
        // 使用流将html写入到文件
        IOUtils.copy(inputStream, outputStream);

    }
}