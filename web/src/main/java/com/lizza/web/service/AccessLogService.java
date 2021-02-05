package com.lizza.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lizza.web.dao.AccessLogDao;
import com.lizza.web.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-05
 */
@Slf4j
@Service
public class AccessLogService {

    @Autowired
    private AccessLogDao accessLogDao;

    private File file = new File("/Users/lizza/Downloads", "lizza.csv");

    private static int offset = 500;

    int lineCount = 0;
    int sfc = 0;
    int ffc = 0;

    public void insertBatch(String jsonStr) {
        JSONArray array = JSONArray.parseArray(jsonStr);
        for (int i = 0; i < array.size(); i++) {
            AccessLog accessLog = new AccessLog();
            accessLog.setJsonResult(array.getJSONObject(i).toJSONString());
            accessLogDao.insert(accessLog);
        }
    }

    public String updateOldData(Integer start, Integer end,
                        String uidKey, String phoneKey, String userIdKey,
                        String uidRegex, String phoneRegex, String userIdRegex) throws Exception {
        // 记录总共更新数据
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (start < end) {
            start++;
            String uid = "";
            String userId = "";
            String contactPhone = "";
            String data = "";
            try {
                data = reader.readLine();
                Pattern uidPattern = Pattern.compile(uidRegex);
                Matcher uidMatcher = uidPattern.matcher(data);
                while (uidMatcher.find()) {
                    JSONObject uidObject = JSONObject.parseObject("{" + uidMatcher.group() + "}");
                    uid = uidObject.getString(uidKey);
                    break;
                }

                Pattern userIdPattern = Pattern.compile(userIdRegex);
                Matcher userIdMatcher = userIdPattern.matcher(data);
                while (userIdMatcher.find()) {
                    JSONObject userIdObject = JSONObject.parseObject("{" + userIdMatcher.group() + "}");
                    userId = userIdObject.getString(userIdKey);
                    break;
                }

                Pattern phonePattern = Pattern.compile(phoneRegex);
                Matcher phoneMatcher = phonePattern.matcher(data);
                while (phoneMatcher.find()) {
                    JSONObject phoneObject = JSONObject.parseObject("{" + phoneMatcher.group() + "}");
                    contactPhone = phoneObject.getString(phoneKey);
                    break;
                }
                writeFile(data, uid, userId, contactPhone);
            } catch (Exception e) {
                e.printStackTrace();
                writeFile(data, uid, userId, contactPhone);
            }
        }
        return null;
    }

    private void writeFile (String data, String uid, String userId, String contactPhone) {
        lineCount++;
        if (lineCount >= 250000) {
            lineCount = 0;
            ffc++;
            sfc++;
        }
        uid = uid == null ? "" : uid;
        userId = userId == null ? "" : userId;
        contactPhone = contactPhone == null ? "" : contactPhone;
        String path = "/Users/lizza/Downloads";
        if (StringUtils.isEmpty(uid) && StringUtils.isEmpty(userId) && StringUtils.isEmpty(contactPhone)) {
            path += "/failure_" + ffc + ".txt";
        } else {
            path += "/success_" + sfc + ".txt";
        }
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(path, true));
            log.info(data + "\t||||\t" + uid + "\t" + userId + "\t" + contactPhone);
            writer.write(data + "\t||||\t" + uid + "\t" + userId + "\t" + contactPhone);
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private int buildAccessLogAndUpdate(int result, AccessLog accessLog, String uid, String userId, String contactPhone) {
        uid = uid == null ? "" : uid;
        userId = userId == null ? "" : userId;
        contactPhone = contactPhone == null ? "" : contactPhone;
        if (StringUtils.isEmpty(uid) && StringUtils.isEmpty(userId) && StringUtils.isEmpty(contactPhone)) {
            return 0;
        }
        accessLog.setUid(uid);
        accessLog.setUserId(userId);
        accessLog.setContactPhone(contactPhone);
        result += accessLogDao.updateLog(accessLog);
        return result;
    }
}
