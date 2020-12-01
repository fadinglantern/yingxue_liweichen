package com.weichen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.weichen"})
@MapperScan(basePackages = {"com.weichen.dao","com.weichen.log.dao"})
public class YingxueLiweichenApplication {

	public static void main(String[] args) {
		SpringApplication.run(YingxueLiweichenApplication.class, args);
	}

}
