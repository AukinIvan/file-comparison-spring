package com.testtask.filecomparison.service;

import com.testtask.filecomparison.ResultComparison;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {
    private final Logger logger;

    @Autowired
    public ViewService(Logger logger) {
        this.logger = logger;
    }

    public void error(String message) {
        logger.error(message);
        System.out.println(message);
    }

    public void printResult(List<ResultComparison> result) {
        if (result.isEmpty()) {
            System.out.println("Files are equal");
        } else {
            result.forEach(System.out::println);
        }
    }
}
