package com.lizza.demo.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Desc:
 * @author: lizza1643@gmail.com
 * @date: 2020-12-22
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @PostMapping("msg")
    public String msg(String msg) {
        return "The MSG you entered is: " + msg;
    }

    @GetMapping("regex")
    public String regex(String regex) {
        return "Hello, Spring Boot!";
    }
}
