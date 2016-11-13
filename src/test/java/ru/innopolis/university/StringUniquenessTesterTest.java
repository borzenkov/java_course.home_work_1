package ru.innopolis.university;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class StringUniquenessTesterTest {
    @Test
    public void testTest() throws Exception {
        Set<String> set = new HashSet<>();
        assertTrue(StringUniquenessTester.test("q", set));
        assertFalse(StringUniquenessTester.test("q", set));
    }

}