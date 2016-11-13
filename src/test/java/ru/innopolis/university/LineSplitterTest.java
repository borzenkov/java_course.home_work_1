package ru.innopolis.university;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.innopolis.university.Constants.PUNCTUATION_MARKS;

public class LineSplitterTest {
    @Test
    public void testSplit() throws Exception {
        String string = "строка из слов";
        String[] strings = {"строка", "из", "слов"};
        assertArrayEquals(LineSplitter.split(string, PUNCTUATION_MARKS), strings);

        string = "string with words";
        String[] strings1 = {};
        assertArrayEquals(LineSplitter.split(string, PUNCTUATION_MARKS), strings1);

        string = "   ";
        String[] strings2 = {};
        assertArrayEquals(LineSplitter.split(string, PUNCTUATION_MARKS), strings2);
    }

}