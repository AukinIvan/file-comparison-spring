package com.testtask.filecomparison;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileComparisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileComparisonApplication.class, args);
    }

    @Bean
    public Logger logger() {
        return LogManager.getLogger();
    }
}
