package com.lizza.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lizza.web.dao.UserDao;
import com.lizza.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-01-28
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String addUser (User user) {
        int i = userDao.insert(user);
        return "插入了" + i + "条数据";
    }
}
