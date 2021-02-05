package com.lizza.web.dao;

import com.lizza.web.entity.AccessLog;

import java.util.List;

public interface AccessLogDao {
    int insert(AccessLog accessLog);

    List<AccessLog> getByPage(Integer start, Integer end);

    int updateLog(AccessLog accessLog);
}
