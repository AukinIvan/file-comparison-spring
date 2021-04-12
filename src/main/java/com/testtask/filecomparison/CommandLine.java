package com.testtask.filecomparison;

import com.testtask.filecomparison.service.FileComparisonService;
import com.testtask.filecomparison.service.ViewService;
import com.testtask.filecomparison.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

@Component
public class CommandLine implements CommandLineRunner {
    private final FileComparisonService fileComparisonService;
    private final CheckUtil checkUtil;
    private final ViewService viewService;

    @Autowired
    public CommandLine(FileComparisonService fileComparisonService, CheckUtil checkUtil, ViewService viewService) {
        this.fileComparisonService = fileComparisonService;
        this.checkUtil = checkUtil;
        this.viewService = viewService;
    }

    @Override
    public void run(String... args) {
        try {
            var parameters = checkUtil.parserParameters(args);
            var result = fileComparisonService.compare(parameters);
            viewService.printResult(result);
        } catch (NoSuchFileException e) {
            viewService.error("Not found file - " + e.getFile());
        } catch (AccessDeniedException e) {
            viewService.error("No access on read file - " + e.getFile());
        } catch (IllegalArgumentException | IOException e) {
            viewService.error(e.getMessage());
        } catch (Exception e) {
            viewService.error("Unknown error");
        }
    }
}
