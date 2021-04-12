package com.testtask.filecomparison.service;

import com.testtask.filecomparison.Parameters;
import com.testtask.filecomparison.ResultComparison;
import com.testtask.filecomparison.util.FileUtil;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileComparisonService {
    private final FileUtil fileUtil;

    @Autowired
    public FileComparisonService(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public List<ResultComparison> compare(Parameters parameters) throws Exception {
        var resultList = new ArrayList<ResultComparison>();
        var fileOriginLines = fileUtil.readAllLines(parameters.getPathOriginFile());
        var fileModifiedLines = fileUtil.readAllLines(parameters.getPathModifiedFile());

        Patch<String> patch = DiffUtils.diff(fileOriginLines, fileModifiedLines);

        if (patch.getDeltas().size() != 0) {
            for (Delta<?> delta : patch.getDeltas()) {
                var type = delta.getType();

                ResultComparison resultComparison = new ResultComparison();
                resultComparison.setPosition(delta.getOriginal().getPosition() + 1);
                resultComparison.setType(type);

                switch (type) {
                    case INSERT:
                        resultComparison.setLine(delta.getRevised().getLines().toString());
                        break;
                    case DELETE:
                        resultComparison.setLine(delta.getOriginal().getLines().toString());
                        break;
                    case CHANGE:
                        resultComparison.setLine(delta.getOriginal().getLines().toString() + " to " + delta.getRevised().getLines().toString());
                }

                resultList.add(resultComparison);
            }
        }

        return resultList;
    }
}
