package com.lizza.web.controller;

import com.lizza.web.entity.User;
import com.lizza.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-01-28
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public String addUser(User user) {
        return userService.addUser(user);
    }

    @PostMapping("regex")
    public String regex(Integer start, Integer end,
                        String uidKey, String phoneKey, String userIdKey,
                        String uidRegex, String phoneRegex, String userIdRegex) {
        return userService.regex(start, end, uidKey, phoneKey, userIdKey, uidRegex, phoneRegex, userIdRegex);
    }
}
