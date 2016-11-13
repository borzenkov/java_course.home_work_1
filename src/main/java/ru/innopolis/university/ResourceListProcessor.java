package ru.innopolis.university;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.innopolis.university.Constants.THREADS_MAX_NUMBER;

/**
 * Этот класс работает со списком урлов, который передается в конструктор. Метод process обрабатывает файл, лежащий по
 * урлу, в отдельном потоке, считывает из него слова, состоящие из букв алфавита русского языка. В случае повторного
 * считывания слова, метод завершает работу.
 */
public class ResourceListProcessor {

    private String[] urls = null;
    private Set<String> uniqueWordsSet = new HashSet<>();
    private boolean stopThreadsWhenNonUniqueWordFound = true;
    public Lock lock = new ReentrantLock();

    public ResourceListProcessor(String[] urls, boolean stopThreadsWhenNonUniqueWordFound) {
        this.urls = urls;
        this.stopThreadsWhenNonUniqueWordFound = stopThreadsWhenNonUniqueWordFound;
    }

    /**
     * Этот метод запускает отдельный поток для обработки каждого урла в списке urls.
     */
    public void process() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_MAX_NUMBER);
        for (String url: urls) {
            Thread thread = new ResourceProcessingThread(url, uniqueWordsSet, stopThreadsWhenNonUniqueWordFound, lock);
            //thread.start();
            executorService.execute(thread);
        }
        executorService.shutdown();
    }

    public int getSetSize() {
        lock.lock();
        try {
            return uniqueWordsSet.size();
        } finally {
            lock.unlock();
        }
        /*synchronized (uniqueWordsSet) {
            return uniqueWordsSet.size();
        }*/
    }
}