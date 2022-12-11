package com.ssafy.bootudy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import com.ssafy.bootudy.model.mapper.MemberMapper;

@Configuration
@MapperScan(basePackageClasses = MemberMapper.class)
public class RootContextConfig {

}
