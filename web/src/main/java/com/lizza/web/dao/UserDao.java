package com.lizza.web.dao;

import com.lizza.web.entity.User;

import java.util.List;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-01-28
 */
public interface UserDao {

    int insert(User user);

    User select(int id);

    List<User> list(Integer start, Integer end);
}
