package com.smithies.angularreports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AngularReports {

    public static void main(String[] args) {
        SpringApplication.run(AngularReports.class, args);
    }
}
