package com.ex.novel.util.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class NovelBlockingQueue {
    public NovelBlockingQueue() {}
    private static class Singleton{
        private static LinkedBlockingQueue linkedBlockingQueue=new LinkedBlockingQueue();
    }
    public static LinkedBlockingQueue getInstance(){
        return Singleton.linkedBlockingQueue;
    }
}
