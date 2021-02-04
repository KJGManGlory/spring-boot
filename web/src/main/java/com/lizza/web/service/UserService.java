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

    public String regex(Integer start, Integer end,
                        String uidKey, String phoneKey, String userIdKey,
                        String uidRegex, String phoneRegex, String userIdRegex) {
        JSONArray array = new JSONArray();
        while (start <= end) {
            List<User> list = userDao.list(start, start++);
            for (User user : list) {
                String jsonResult = user.getExtInfo();
                JSONObject object = new JSONObject();
                String uid = "";
                String userId = "";
                String contactPhone = "";

                Pattern uidPattern = Pattern.compile(uidRegex);
                Matcher uidMatcher = uidPattern.matcher(jsonResult);
                while (uidMatcher.find()) {
                    JSONObject uidObject = JSONObject.parseObject("{" + uidMatcher.group() + "}");
                    uid = uidObject.getString(uidKey);
                    object.put(uidKey, uid);
                    array.add(object);
                    break;
                }

                Pattern userIdPattern = Pattern.compile(userIdRegex);
                Matcher userIdMatcher = userIdPattern.matcher(jsonResult);
                while (userIdMatcher.find()) {
                    JSONObject userIdObject = JSONObject.parseObject("{" + userIdMatcher.group() + "}");
                    userId = userIdObject.getString(userIdKey);
                    object.put(userIdKey, userId);
                    array.add(object);
                    break;
                }

                Pattern phonePattern = Pattern.compile(phoneRegex);
                Matcher phoneMatcher = phonePattern.matcher(jsonResult);
                while (phoneMatcher.find()) {
                    JSONObject phoneObject = JSONObject.parseObject("{" + phoneMatcher.group() + "}");
                    contactPhone = phoneObject.getString(phoneKey);
                    object.put(phoneKey, contactPhone);
                    array.add(object);
                    break;
                }
            }

        }

        return array.toJSONString();
    }
}
