package com.example.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "se.magnus", "com.example" })
public class ProductApp {

  public static void main(String[] args) {
    SpringApplication.run(ProductApp.class, args);
  }

}
