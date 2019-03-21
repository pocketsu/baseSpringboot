package com.sz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.sz")
public class BaseSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseSpringbootApplication.class, args);
	}

}
