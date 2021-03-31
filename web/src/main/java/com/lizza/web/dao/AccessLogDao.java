package com.lizza.web.dao;

import com.lizza.web.entity.AccessLog;

import java.util.List;

public interface AccessLogDao {
    List<AccessLog> getAccessLogsByUid(String uid);
}
