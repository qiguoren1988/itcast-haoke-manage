package com.mmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author solang
 * @date 2020-01-18 6:43
 */
@MapperScan("com.mmall.dao")
@Configuration
public class MybatisConfig {
}
