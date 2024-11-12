package com.example.temp;

import java.security.Security;
import java.util.TimeZone;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.temp.conf.SessionListener;


@EnableScheduling
@SpringBootApplication
public class TempProjectApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TempProjectApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TempProjectApplication.class, args);
	}
	
	/*
	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}
	*/
}
