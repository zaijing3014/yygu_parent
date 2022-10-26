package com.atguigu.yygh.cmn.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzj
 * @date 2022/10/21
 */
@Configuration
@MapperScan("com.atguigu.yygh.cmn.mapper")
@ComponentScan(basePackages = "com.attguigu")
@EnableDiscoveryClient
public class DicConfig {
}
