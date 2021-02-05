package com.lizza.web.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-05
 */
@Data
public class AccessLog {

    private Integer id;
    private String uid;
    private String userId;
    private String contactPhone;
    private String jsonResult;
}
