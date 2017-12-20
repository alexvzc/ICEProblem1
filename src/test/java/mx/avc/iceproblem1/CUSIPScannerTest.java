/*
 * Copyright (C) 2017 Alejandro Vazquez

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package mx.avc.iceproblem1;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;
import static mx.avc.iceproblem1.Utils.pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author alexv
 */
public class CUSIPScannerTest {
    private static final Logger LOGGER = getLogger(CUSIPScannerTest.class);

    private static final String TEST_INPUT =
            "11223344\n1.992\n2.233\n1.221\n0.123\n" +
            "22334455\n3.232\n3.542\n2.991\n3.001\n" +
            "AABBCCDD\n2.211\n" +
            "ZZYYXXVV\n0.010\n0.009\n0.007\n1.001";

    @Test
    public void testNext() {
        LOGGER.info("Testing next()");
        Reader in = new StringReader(TEST_INPUT);
        Entry<String, String> EXPECTED_RESULT = pair("11223344", "0.123");

        CUSIPScanner scanner = new CUSIPScanner(in);

        Entry<String, String> result = scanner.next();

        assertNotNull(result);
        assertEquals(EXPECTED_RESULT, result);
    }

    @Test
    public void testHasNext() {
        LOGGER.info("Testing hasNext()");
        Reader in = new StringReader(TEST_INPUT);

        CUSIPScanner scanner = new CUSIPScanner(in);

        boolean result = scanner.hasNext();

        assertTrue(result);
    }

    @Test
    public void testNextEnd() {
        LOGGER.info("Testing next() - at the end");
        Reader in = new StringReader(TEST_INPUT);

        CUSIPScanner scanner = new CUSIPScanner(in);

        IntStream.range(0, 4).forEach(i -> scanner.next());
        Optional<Throwable> throwed = Optional.empty();

        try {
            scanner.next();
        } catch(Throwable t) {
            throwed = Optional.of(t);
        }

        assertTrue(throwed.isPresent());
        assertTrue(throwed.get() instanceof NoSuchElementException);
    }

    @Test
    public void testHasNextEnd() {
        LOGGER.info("Testing hasNext() - at the end");
        Reader in = new StringReader(TEST_INPUT);

        CUSIPScanner scanner = new CUSIPScanner(in);

        IntStream.range(0, 4).forEach(i -> scanner.next());
        boolean result = scanner.hasNext();

        assertFalse(result);
    }

    @Test
    public void testNextAll() {
        LOGGER.info("Testing next() - all values");
        Reader in = new StringReader(TEST_INPUT);
        List<Entry<String, String>> EXPECTED_RESULTS = asList(
            pair("11223344", "0.123"),
            pair("22334455", "3.001"),
            pair("AABBCCDD", "2.211"),
            pair("ZZYYXXVV", "1.001"));

        CUSIPScanner scanner = new CUSIPScanner(in);

        EXPECTED_RESULTS.forEach(p -> assertEquals(p, scanner.next()));
    }

    @Test
    public void testHasNextAll() {
        LOGGER.info("Testing hasNext() - all values");
        Reader in = new StringReader(TEST_INPUT);
        List<Entry<String, String>> EXPECTED_RESULTS = asList(
            pair("11223344", "0.123"),
            pair("22334455", "3.001"),
            pair("AABBCCDD", "2.211"),
            pair("ZZYYXXVV", "1.001"));

        CUSIPScanner scanner = new CUSIPScanner(in);
        List<Entry<String, String>> results = new ArrayList<>();

        while(scanner.hasNext()) {
            results.add(scanner.next());
        }

        assertEquals(EXPECTED_RESULTS, results);
    }
}
