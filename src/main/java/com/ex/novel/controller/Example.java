package com.ex.novel.controller;

import com.ex.novel.service.ScanNovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Example {
    @Autowired
    private ScanNovelService scanNovelService;
    @RequestMapping("/abc")
    @ResponseBody
    public String abc(){
        scanNovelService.scan();
        return "123";
    }
}
