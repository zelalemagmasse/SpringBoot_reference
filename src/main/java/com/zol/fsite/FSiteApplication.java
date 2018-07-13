package com.zol.fsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zol.fsite")
public class FSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FSiteApplication.class, args);
	}
}
