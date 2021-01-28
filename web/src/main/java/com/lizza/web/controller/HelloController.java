package com.lizza.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-01-28
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}