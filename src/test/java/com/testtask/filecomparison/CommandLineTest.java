package com.testtask.filecomparison;

import com.testtask.filecomparison.service.FileComparisonService;
import com.testtask.filecomparison.service.ViewService;
import com.testtask.filecomparison.util.CheckUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommandLineTest {
    private static final Parameters PARAMETERS = new Parameters();
    private static final String[] ARGS = new String[]{"123", "456"};

    @InjectMocks
    CommandLine commandLine;

    @Mock
    ViewService viewService;

    @Mock
    CheckUtil checkUtil;

    @Mock
    FileComparisonService fileComparisonService;

    @BeforeAll
    static void init() {
        PARAMETERS.setPathOriginFile("123");
        PARAMETERS.setPathModifiedFile("456");
    }

    @Test
    void runThrowAccessDeniedException() throws Exception {
        Mockito.when(checkUtil.parserParameters(ARGS)).thenReturn(PARAMETERS);
        Mockito.when(fileComparisonService.compare(PARAMETERS)).thenThrow(new AccessDeniedException("123"));
        commandLine.run(ARGS);
        Mockito.verify(viewService, Mockito.times(1)).error("No access on read file - 123");
    }

    @Test
    void runThrowNoSuchFileException() throws Exception {
        Mockito.when(checkUtil.parserParameters(ARGS)).thenReturn(PARAMETERS);
        Mockito.when(fileComparisonService.compare(PARAMETERS)).thenThrow(new NoSuchFileException("123"));
        commandLine.run(ARGS);
        Mockito.verify(viewService, Mockito.times(1)).error("Not found file - 123");
    }
}