package com.xuecheng.content.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mr.Z
 * @version 1.0
 * @description Freemarker入门程序
 * @create 2023-07-31 19:04
 * @github https://github.com/Ragnarokoo
 */
@Controller
public class FreemarkerController {

    @GetMapping("/testfreemarker")
    public ModelAndView test() {

        ModelAndView modelAndView = new ModelAndView();
        // 指定模型
        modelAndView.addObject("name", "小明");
        // 指定模版
        modelAndView.setViewName("test"); // 根据视图名称加上.ftl找到视图模版
        return modelAndView;
    }
}