package com.ssafy.jytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.jytrip.aop.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("http://localhost:5173",
						"http://ec2-43-201-109-248.ap-northeast-2.compute.amazonaws.com", 
						"http://jytrip.monster",
						"http://www.jytrip.monster",
						"https://www.jytrip.monster",
						"https://jytrip.monster")
				.allowedMethods("GET", "POST");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor())
				.addPathPatterns("/board/write", "/member/requestNewAccessToken",
				"/trip/saveTripList", "/trip/getTripList"); // 적용할 URL 패턴 지정
	}

	@Bean
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}
}