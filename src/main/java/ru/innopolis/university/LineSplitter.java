package ru.innopolis.university;

import java.util.regex.Pattern;

public class LineSplitter {

    public static String[] split(String line, String delimiter) {
        Pattern pattern = Pattern.compile(delimiter);
        return pattern.split(line);
    }
}
