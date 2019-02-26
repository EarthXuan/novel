package com.ex.novel.service.impl;

import com.ex.novel.dao.ChapterMapper;
import com.ex.novel.dao.NovelMapper;
import com.ex.novel.domain.Chapter;
import com.ex.novel.domain.Novel;
import com.ex.novel.domain.NovelExample;
import com.ex.novel.service.ScanNovelService;
import com.ex.novel.util.queue.NovelBlockingQueue;
import com.ex.novel.util.thread.NovelThreadPool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service("scanNovelService")
public class ScanNovelServiceImpl implements ScanNovelService {
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private NovelMapper novelMapper;

    @Override
    @Transactional
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

        //
        List<Novel> novels = new LinkedList<>();
        for (Element element : aList) {
            Novel novel = new Novel();
            novel.setNovelname(element.text());
            novel.setUrl(element.attr("href"));
            novels.add(novel);
//                NovelBlockingQueue.getInstance().put(element.attr("href") + "," + element.text());
//            System.out.println(element.attr("href") + "," + element.text());
        }
//        novelMapper.insertBatch(novels);

        NovelExample novelExample=new NovelExample();
        novels=novelMapper.selectByExample(novelExample);

        for(Novel novel:novels){
            try {
                NovelBlockingQueue.getInstance().put(novel.getId()+","+novel.getUrl() + "," + novel.getUrl());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 10; i++) {
            final int index = i;
            NovelThreadPool.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Object object = NovelBlockingQueue.getInstance().poll();
                        if (object == null)
                            break;
                        String[] val = ((String) object).split(",");
                        System.out.println(Thread.currentThread().getName() + "i:" + val[0] + "," + val[1]);
                        scanChapter(val[0],val[1], val[2]);
                    }
                }
            });
        }

    }

    @Transactional
    public void scanChapter(String novelId,String url, String name) {
        Document document = null;
        try {
            document = Jsoup.connect(url.contains("http://www.xbiquge.la") ? url : ("http://www.xbiquge.la" + url)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element listElement = document.getElementById("list");
        List<Element> list = listElement.getElementsByTag("a");
        List<Chapter>chapters=new LinkedList<>();
        for (Element element : list) {
            Chapter chapter=new Chapter();
            chapter.setNovelid(Integer.parseInt(novelId));
            chapter.setUrl(element.attr("href"));
            chapter.setChaptername(element.text());
            chapter.setContent(scanContent(element.attr("href"), element.text()));
            chapters.add(chapter);
            System.out.println(name + ":" + element.attr("href") + "," + element.text());
        }
        chapterMapper.insertBatch(chapters);
    }

    public String scanContent(String url, String name) {
        Document document = null;
        try {
            document = Jsoup.connect(url.contains("http://www.xbiquge.la") ? url : ("http://www.xbiquge.la" + url)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element contentElement = document.getElementById("content");
        if(contentElement==null){
            return "";
        }
        System.out.println(name + ":" + contentElement.text());
        return contentElement.text()==null?"":contentElement.text();
    }

}

