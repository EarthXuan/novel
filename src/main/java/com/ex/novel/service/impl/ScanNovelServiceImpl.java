package com.ex.novel.service.impl;

import com.ex.novel.service.ScanNovelService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service("scanNovelService")
public class ScanNovelServiceImpl implements ScanNovelService {
    LinkedBlockingQueue queue = new LinkedBlockingQueue();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Override
    public void scan() {
        //笔趣阁全部小说页面
        String url = "http://www.xbiquge.la/xiaoshuodaquan/";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Element> list = document.getAllElements();
        Element body = document.body();
        Element main = body.getElementById("main");
        List<Element> aList = main.getElementsByTag("a");
        for (Element element : aList) {
            try {
                queue.put(element.attr("href") + "," + element.text());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(element.attr("href") + "," + element.text());
        }

        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Object object =  queue.poll();
                        if(object==null)
                            break;
                        String[] val = ((String)object).split(",");
                        System.out.println(Thread.currentThread().getName() + "i:" + val[0] + "," + val[1]);
                        scanChapter(val[0],val[1]);
                    }
                }
            });
        }

    }

    public void scanChapter(String url,String name){
        Document document = null;
        try {
            document = Jsoup.connect(url.contains("http://www.xbiquge.la")?url:("http://www.xbiquge.la"+url)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element listElement=document.getElementById("list");
        List<Element> list=listElement.getElementsByTag("a");
        for(Element element:list){
            System.out.println(name+":"+element.attr("href")+","+element.text());
            scanContent(element.attr("href"),element.text());
        }
    }

    public void scanContent(String url,String name){
        Document document = null;
        try {
            document = Jsoup.connect(url.contains("http://www.xbiquge.la")?url:("http://www.xbiquge.la"+url)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element contentElement=document.getElementById("content");
            System.out.println(name+":"+contentElement.text());
    }

}

