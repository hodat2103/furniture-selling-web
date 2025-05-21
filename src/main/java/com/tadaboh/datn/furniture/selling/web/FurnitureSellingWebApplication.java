package com.tadaboh.datn.furniture.selling.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tadaboh.datn.furniture.selling.web")
public class FurnitureSellingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurnitureSellingWebApplication.class, args);
	}

}
