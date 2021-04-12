package com.testtask.filecomparison.util;

import com.testtask.filecomparison.Parameters;
import org.springframework.stereotype.Component;

@Component
public class CheckUtil {

    public Parameters parserParameters(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not all parameters are specified. Please run app with parameters");
        }

        Parameters parameters = new Parameters();
        parameters.setPathOriginFile(args[0]);
        parameters.setPathModifiedFile(args[1]);

        return parameters;
    }
}
