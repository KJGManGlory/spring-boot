package com.lizza.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-03-30
 */
@Data
@Accessors(chain = true)
public class AccessLog {

    /** 标号 **/
    private int id;

    /** 订单no **/
    private String orderNo;

    /** uid **/
    private String uid;

    /** 用户 id **/
    private String userId;

    /** 联系电话 **/
    private String contactPhone;

    /** 返回结果 **/
    private String jsonResult;

    /** 请求接口结果, 默认0 **/
    private Integer resultCode;

    /** 接口name **/
    private String api;

    /** 请求IP **/
    private String accessIp;

    /** 创建时间 **/
    private LocalDateTime createTime;

    /** 请求id **/
    private String requestId;

    /** 业务来源 **/
    private String bizSource;

    private long interval = Long.MAX_VALUE;

    private int times = 0;

}
