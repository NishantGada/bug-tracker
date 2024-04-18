package com.project.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.project.bugtracker.controller",
        "com.project.bugtracker.pojo",
        "com.project.bugtracker.DAO"
})
public class BugtrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BugtrackerApplication.class, args);
    }
}