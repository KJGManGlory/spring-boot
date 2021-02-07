package com.lizza.web.controller;

import com.lizza.web.entity.AccessLog;
import com.lizza.web.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-05
 */
@RestController
@RequestMapping
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @PostMapping("updateOldData")
    public int updateOldData(Integer start, Integer end,
                        String uidKey, String phoneKey, String userIdKey,
                        String uidRegex, String phoneRegex, String userIdRegex) throws Exception {
        return accessLogService.updateOldData(start, end, uidKey, phoneKey, userIdKey, uidRegex, phoneRegex, userIdRegex);
    }

    @ResponseBody
    @RequestMapping(value = "/updateOldAccessLog")
    public void updateOldAccessLog (@RequestBody AccessLog accessLog) {
        accessLogService.updateOldAccessLog(accessLog);
    }
}
