package com.lizza.web.controller;

import com.lizza.web.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-03-30
 */
@RestController
@RequestMapping("/accessLog")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    public static void main(String[] args) throws Exception {
    	File file = new File("/Users/lizza/Desktop/log_data.log");
        FileInputStream is = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str;
        while((str = reader.readLine()) != null) {
            System.out.println(str);
        }
        //close
        is.close();
        reader.close();
    }
}
