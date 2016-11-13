package ru.innopolis.university;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.innopolis.university.Constants.PUNCTUATION_MARKS;

/**
 * Этот класс описывает процесс обработки отдельного урла. Конструктор принимает на вход урл и множество, которое
 * будет хранить в себе слова, прочитанные из ресурса.
 */
public class ResourceProcessingThread extends Thread {

    private String urlString = null;
    private Set<String> uniqueWordsSet = null;
    private static BooleanBox isWorking = new BooleanBox(true);
    private boolean stopThreadsWhenNonUniqueWordFound;
    private Lock lock = null;

    ResourceProcessingThread(String urlString, Set uniqueWordsSet, boolean stopThreadsWhenNonUniqueWordFound, Lock lock) {
        this.urlString = urlString;
        this.uniqueWordsSet = uniqueWordsSet;
        this.stopThreadsWhenNonUniqueWordFound = stopThreadsWhenNonUniqueWordFound;
        this.lock = lock;
    }

    public void run() {
        processURL();
    }

    /**
     * Этот метод читает ресурс построчно и отправляет строку на обработку.
     */
    private void processURL() {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                lock.lock();
                try {
                    if ( !isWorking.getValue() ) {
                        break;
                    }
                } finally {
                    lock.unlock();
                }
                /*synchronized (uniqueWordsSet) {
                    if ( !isWorking.getValue() ) {
                        break;
                    }
                }*/
                processLine(line);
            }
            br.close();
        } catch (MalformedURLException e) {
            System.out.println("Неверный URL!");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Вставь кабель!");
        }

    }

    /**
     * Этот метод разбивает строку на слова и проверяет уникальность слова при добавлении его во множество.
     * @param line
     */
    private void processLine(String line) {

        String[] wordsInLine = LineSplitter.split(line.toLowerCase(), PUNCTUATION_MARKS);

        for (String word: wordsInLine) {
            if (!word.equals("")) {
                lock.lock();
                try {
                    if( !StringUniquenessTester.test(word, uniqueWordsSet) ) {
                        System.out.println(getName() +" : Встретилось неуникальное слово \"" + word + "\".");
                        if (stopThreadsWhenNonUniqueWordFound) {
                            isWorking.setValue(false);
                            break;
                        }
                    }
                } finally {
                    lock.unlock();
                }
                /*synchronized (uniqueWordsSet) {
                    if( !StringUniquenessTester.test(word, uniqueWordsSet) ) {
                        System.out.println(getName() +" : Встретилось неуникальное слово \"" + word + "\".");
                        if (stopThreadsWhenNonUniqueWordFound) {
                            isWorking.setValue(false);
                            break;
                        }
                    }
                }*/
            }
        }
    }
}
