package com.lizza.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebApplication.class, args);
		System.out.println(BigDecimal.ZERO.intValue());
	}

}