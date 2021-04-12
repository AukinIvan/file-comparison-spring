package com.testtask.filecomparison.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileUtil {

    public List<String> readAllLines(String uriFile) throws Exception {
        Path pathFile = Paths.get(uriFile);
        try {
            return Files.readAllLines(pathFile);
        } catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                throw new IOException(pathFile + " - " + e.getMessage());
            } else {
                throw e;
            }
        }
    }
}
