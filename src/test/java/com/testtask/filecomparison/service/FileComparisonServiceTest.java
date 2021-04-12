package com.testtask.filecomparison.service;

import com.testtask.filecomparison.Parameters;
import com.testtask.filecomparison.ResultComparison;
import com.testtask.filecomparison.util.FileUtil;
import difflib.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FileComparisonServiceTest {
    private static final Parameters PARAMETERS = new Parameters();

    @Mock
    FileUtil fileUtil;

    @InjectMocks
    FileComparisonService fileComparisonService;

    @BeforeAll
    static void init() {
        PARAMETERS.setPathOriginFile(null);
        PARAMETERS.setPathModifiedFile(null);
    }

    @Test
    void compareIdenticalFiles() throws Exception {
        Mockito.when(fileUtil.readAllLines(null)).thenReturn(Collections.emptyList());
        var compare = fileComparisonService.compare(PARAMETERS);

        Assertions.assertTrue(compare.isEmpty());
    }

    @Test
    void compareDifferentFilesChange() throws Exception {
        var resultComparisonChange = new ResultComparison();
        resultComparisonChange.setPosition(1);
        resultComparisonChange.setType(Delta.TYPE.CHANGE);
        resultComparisonChange.setLine("[123] to [321]");

        var expected = List.of(resultComparisonChange);

        Mockito.doReturn(List.of("123"), List.of("321")).when(fileUtil).readAllLines(null);
        var compare = fileComparisonService.compare(PARAMETERS);

        Assertions.assertEquals(expected, compare);
    }

    @Test
    void compareDifferentFilesDeleteAndInsert() throws Exception {
        var resultComparisonDelete = new ResultComparison();
        resultComparisonDelete.setPosition(1);
        resultComparisonDelete.setType(Delta.TYPE.DELETE);
        resultComparisonDelete.setLine("[123]");

        var resultComparisonInsert = new ResultComparison();
        resultComparisonInsert.setPosition(3);
        resultComparisonInsert.setType(Delta.TYPE.INSERT);
        resultComparisonInsert.setLine("[456]");

        var expected = List.of(resultComparisonDelete, resultComparisonInsert);

        Mockito.doReturn(List.of("123", "321"), List.of("321", "456")).when(fileUtil).readAllLines(null);
        var compare = fileComparisonService.compare(PARAMETERS);

        Assertions.assertEquals(expected, compare);
    }
}