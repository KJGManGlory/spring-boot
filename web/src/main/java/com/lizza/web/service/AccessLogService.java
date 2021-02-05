package com.lizza.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lizza.web.dao.AccessLogDao;
import com.lizza.web.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        String uidRegex, String phoneRegex, String userIdRegex) {
        // 记录总共更新数据
        int result = 0;
        int offset = 1;
        while (start < end) {
            List<AccessLog> accessLogList = accessLogDao.getByPage(start, start + offset);
            log.info("Access Log 历史数据更新>>> start id: {}, end id: {}, 查询到的数据条数: {}", start, start + offset, accessLogList.size());
            start += offset;
            int size = accessLogList.size();
            if (size == 0) {
                log.info("Access Log 历史数据更新>>> 未查到任何数据");
                continue;
            }
            String jsonResult = "";
            for (AccessLog accessLog : accessLogList) {
                try {
                    jsonResult = accessLog.getJsonResult();
                    String uid = "";
                    String userId = "";
                    String contactPhone = "";

                    Pattern uidPattern = Pattern.compile(uidRegex);
                    Matcher uidMatcher = uidPattern.matcher(jsonResult);
                    while (uidMatcher.find()) {
                        JSONObject uidObject = JSONObject.parseObject("{" + uidMatcher.group() + "}");
                        uid = uidObject.getString(uidKey);
                        break;
                    }

                    Pattern userIdPattern = Pattern.compile(userIdRegex);
                    Matcher userIdMatcher = userIdPattern.matcher(jsonResult);
                    while (userIdMatcher.find()) {
                        JSONObject userIdObject = JSONObject.parseObject("{" + userIdMatcher.group() + "}");
                        userId = userIdObject.getString(userIdKey);
                        break;
                    }

                    Pattern phonePattern = Pattern.compile(phoneRegex);
                    Matcher phoneMatcher = phonePattern.matcher(jsonResult);
                    while (uidMatcher.find()) {
                        JSONObject phoneObject = JSONObject.parseObject("{" + phoneMatcher.group() + "}");
                        contactPhone = phoneObject.getString(phoneKey);
                        break;
                    }

                    result += buildAccessLogAndUpdate(result, accessLog, uid, userId, contactPhone);

                    log.info("Access Log 历史数据更新>>> 没有获取到 uid, 结构不符合解析器, 原始数据 ID: {}, JsonResult: {}", accessLog.getId(), jsonResult);
                } catch (Exception e) {
                    log.error("Access Log 解析更新失败### 原始数据 ID: {}, JsonResult: {}", accessLog.getId(), jsonResult, e);
                }
            }
            log.info("Access Log 历史数据更新>>> 已更新: {}", result);
        }

        return null;
    }

    private int buildAccessLogAndUpdate(int result, AccessLog accessLog, String uid, String userId, String contactPhone) {
        uid = uid == null ? "" : uid;
        userId = userId == null ? "" : userId;
        contactPhone = contactPhone == null ? "" : contactPhone;
        accessLog.setUid(uid);
        accessLog.setUserId(userId);
        accessLog.setContactPhone(contactPhone);
        result += accessLogDao.updateLog(accessLog);
        return result;
    }
}