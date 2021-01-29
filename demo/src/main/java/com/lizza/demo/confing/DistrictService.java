package com.lizza.demo.confing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-01-29
 */
@Slf4j
@Component
public class DistrictService {

    @PostConstruct
    public void init() {
        log.info("开始加载配置");
        try {
            throw new FileNotFoundException("配置文件未找到");
        } catch (Exception e) {
            log.info("配置文件加载失败: ", e);
        }
        log.info("配置文件加载完成");
    }
}
