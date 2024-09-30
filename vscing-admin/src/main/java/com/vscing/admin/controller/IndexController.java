package com.vscing.admin.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "页面测试";
    }
}
