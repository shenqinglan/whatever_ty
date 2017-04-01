package com.whty.oauth.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application{
	
	public static void main(String[] args) {  
        SpringApplication.run(Application.class);  
    }
}
