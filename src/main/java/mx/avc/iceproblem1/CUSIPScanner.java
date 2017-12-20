/*
 * Copyright (C) 2017 Alejandro Vazquez

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package mx.avc.iceproblem1;

import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static mx.avc.iceproblem1.Utils.pair;

/**
 *
 * @author alexv
 */
public class CUSIPScanner implements Iterator<Entry<String, String>> {

    private static final Pattern CUSIP_PATTERN = compile("[A-Za-z0-9]{8}");
    private static final Pattern PRICE_PATTERN =
            compile("[0-9]*(\\.[0-9]+)?");

    private Scanner source;
    private Optional<Entry<String, String>> lastOne;
    private Optional<String> lastCUSIP;
    private Optional<String> lastPrice;

    public CUSIPScanner(Reader s) {
        source = new Scanner(s);
        lastOne = Optional.empty();
        lastCUSIP = Optional.empty();
        lastPrice = Optional.empty();
    }

    private Optional<Entry<String, String>> fetchNextOne() {
        while(source.hasNextLine()) {
            String line = source.nextLine();
            Optional<String> lastOfSomething = Optional.of(line);
            Matcher m = CUSIP_PATTERN.matcher(line);

            if(m.matches()) {
                Optional<Entry<String, String>> found =
                        lastCUSIP.flatMap(c -> lastPrice.map(p -> pair(c, p)));

                lastCUSIP = lastOfSomething;

                if(found.isPresent()) {
                    lastPrice = Optional.empty();
                    return found;
                }
            } else {
                m.usePattern(PRICE_PATTERN);

                if(m.matches()) {
                    lastPrice = lastOfSomething;
                }
            }
        }


        Optional<Entry<String, String>> found =
                lastCUSIP.flatMap(c -> lastPrice.map(p -> pair(c, p)));

        lastCUSIP = Optional.empty();
        lastPrice = Optional.empty();

        return found;
    }

    @Override
    public boolean hasNext() {
        if(lastOne.isPresent()) {
            return true;
        }
        lastOne = fetchNextOne();
        return lastOne.isPresent();
    }

    @Override
    public Entry<String, String> next() {
        if(lastOne.isPresent()) {
            Entry<String, String> previous = lastOne.get();
            lastOne = Optional.empty();
            return previous;
        }
        return fetchNextOne().orElseThrow(() -> new NoSuchElementException());
    }


}
