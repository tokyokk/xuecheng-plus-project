package com.xuecheng.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author ragnarok
 * @version 1.0
 * @description 跨域处理配置类
 * @create 2023-07-01 18:35
 * @github https://github.com/Ragnarokoo
 */
@Configuration
public class GlobalCorsConfig
{
    /**
     * @author ragnarok
     * @description 允许跨域调用的过滤器
     * @param
     * @return CorsFilter
     * @date 2023/7/1 18:39
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        //替换这个
        config.addAllowedOriginPattern("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }
}