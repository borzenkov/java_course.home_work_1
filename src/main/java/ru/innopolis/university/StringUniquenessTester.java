package ru.innopolis.university;

import java.util.Set;

/**
 * Created by imac on 13.11.16.
 */
public class StringUniquenessTester {

    public static boolean test(String string, Set<String> set) {
        return set.add(string);
    }
}
