package com.atguigu.yygh.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzj
 * @date 2022/10/17
 */
@Configuration
@MapperScan("com.atguigu.yygh.hosp.mapper")
@ComponentScan(basePackages = {"com.attguigu"})
public class commonConfig {
    /**
     * 分页插件1
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
