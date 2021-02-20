package com.lizza.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-19
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "Hello, MVC!";
    }
}
