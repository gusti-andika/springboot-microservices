package com.example.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"se.magnus", "com.example.review"})
public class ReviewApp {

	public static void main(String[] args) {
		SpringApplication.run(ReviewApp.class, args);
	}

}
