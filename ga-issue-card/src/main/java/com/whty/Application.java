package com.whty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Application
 * @author Administrator
 * @date 2017-3-3 上午9:56:39
 * @Description TODO(框架入口主函数)
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
	
	public static void main(String[] args) {  
        SpringApplication.run(Application.class);  
    }
}
