package com.example.homeworkspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class HomeWorkSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeWorkSpringApplication.class, args);
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (!registry.hasMappingForPattern("/assets/**")) {
//            registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
//        }
//    }
}
