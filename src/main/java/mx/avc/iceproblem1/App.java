/*
 * Copyright (C) 2017 Alejandro Vazquez

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package mx.avc.iceproblem1;

import com.google.devtools.common.options.OptionsParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author alexv
 */
public class App {
    private static final Logger LOGGER = getLogger(App.class);

    private static void processFile(String file, PrintWriter out)
            throws IOException {
        Path path = Paths.get(file);

        if(!Files.isReadable(path)) {
            out.println("Cannot read from " + file);
            return;
        }

        try(Reader in = Files.newBufferedReader(path)) {

            CUSIPScanner scanner = new CUSIPScanner(in);
            scanner.forEachRemaining(p -> out.format(
                    "CUSIP: %s; Last price: %s\n", p.getKey(), p.getValue()));

        }
    }

    public static void main(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(AppOptions.class);
        parser.parseAndExitUponError(args);
        AppOptions options = parser.getOptions(AppOptions.class);
        String inputfilename = options.getInputFilename();

        if(inputfilename.isEmpty()) {
            printUsage(parser);
            return;
        }

        PrintWriter out = System.console() != null ?
                System.console().writer() : new PrintWriter(System.out);

        try {
            processFile(inputfilename, out);

        } catch (IOException ex) {
            LOGGER.error("IOException found", ex);
            printUsage(parser);
        }
    }

    private static void printUsage(OptionsParser parser) {
        PrintWriter out = System.console() != null ?
                System.console().writer() : new PrintWriter(System.out);

        out.println("Usage: java -jar iceproblem1.jar -i inputfile");
        out.println(parser.describeOptions(Collections.emptyMap(),
                        OptionsParser.HelpVerbosity.LONG));
    }

}
