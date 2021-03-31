package com.lizza.web.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.lizza.web.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-05
 */
@Slf4j
@Service
public class AccessLogService {

    static final Map<String, List<AccessLog>> DATA_MAP = Maps.newHashMap();

    static {
        try {
            // 读取文件
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/lizza/Downloads/Misc/data_505619_20210330230804.csv"))));
            String str;
            while ((str = reader.readLine()) != null) {
                String[] arr = str.split(",");
                String uid = arr[1].replaceAll("\"", "");
                if (StrUtil.isBlank(uid)) {
                    continue;
                }
                AccessLog accessLog = new AccessLog();
                accessLog.setId(Integer.parseInt(arr[0].replaceAll("\"", "").replaceAll("\uFEFF", "")))
                        .setUid(uid)
                        .setCreateTime(LocalDateTime.parse(arr[2].replaceAll("\"", ""), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
                List<AccessLog> list = DATA_MAP.get(uid);
                if (CollectionUtils.isEmpty(list)) {
                    list = new ArrayList<>();
                    list.add(accessLog);
                    DATA_MAP.put(uid, list);
                } else {
                    list.add(accessLog);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getRightIds() throws Exception{
        List<Integer> result = new ArrayList<>();
        // 读取文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/lizza/Desktop/log_data.log"))));
        List<String> list = new ArrayList<>(512);
        String str;
        while((str = reader.readLine()) != null) {
            list.add(str);
        }
        reader.close();

        // 查库, 获取每一个 uid 对应的最近的时间的 id
        List<AccessLog> findList = new ArrayList<>(512);
        Set<String> uidSet = new HashSet<>();
        for (int i = 0; i < 331; i++) {
            String item = list.get(i);
            String[] arr = item.split("@@");
            String uid = arr[0];
            if (uidSet.contains(uid)) {
                continue;
            }
            LocalDateTime createTime = LocalDateTime.parse(arr[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            // 根据 uid 获取数据集
            List<AccessLog> logList = DATA_MAP.get(uid);
            if (CollectionUtils.isEmpty(logList)) {
                continue;
            }
            AccessLog accessLog = new AccessLog();
            for (AccessLog log : logList) {
                long interval = Duration.between(createTime, log.getCreateTime()).toMillis();
                if (Math.abs(interval) < accessLog.getInterval()) {
                    accessLog = log;
                    accessLog.setInterval(Math.abs(interval));
                    int times = accessLog.getTimes();
                    accessLog.setTimes(++times);
                }
            }
            findList.add(accessLog);
            uidSet.add(uid);
        }
        System.out.println(JSONArray.toJSONString(uidSet));
        System.out.println(JSONArray.toJSONString(findList));

        return result;
    }

    public static void main(String[] args) throws Exception {
        List<Integer> list = Arrays.asList(18226395, 18233491, 18256762, 18290474, 18307083, 18309717, 18314135, 18341274, 18346772, 18382975, 18404628, 18404971, 18540009, 18547096, 18548144, 18554419, 18559062, 18560430, 18582292);
        for (int i = 0; i < list.size() - 1; i++) {
            int start = list.get(i) + 1;
            int end = list.get(i+1) - 1;
        }
    }
}
