package com.ssafy.bootudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.bootudy.interceptor.PagingInterceptor;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
	@Autowired
	PagingInterceptor pi;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 경로의 깊이에 상관 없이 list라고 요청이 온다면...
		registry.addInterceptor(pi).addPathPatterns("/**/list");
	}
}
