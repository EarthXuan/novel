package com.ex.novel.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NovelThreadPool {
    private static class Singleton{
        private static ExecutorService executorService= Executors.newFixedThreadPool(10);
    }
    public static ExecutorService getInstance(){
        return Singleton.executorService;
    }
}
