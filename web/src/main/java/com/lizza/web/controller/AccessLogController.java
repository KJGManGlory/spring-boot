package com.lizza.web.controller;

import com.lizza.web.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("insertBatch")
    public void insertBatch(@RequestBody String json) {
        accessLogService.insertBatch(json);
    }

    @PostMapping("updateOldData")
    public String updateOldData(Integer start, Integer end,
                        String uidKey, String phoneKey, String userIdKey,
                        String uidRegex, String phoneRegex, String userIdRegex) throws Exception {
        return accessLogService.updateOldData(start, end, uidKey, phoneKey, userIdKey, uidRegex, phoneRegex, userIdRegex);
    }
}
