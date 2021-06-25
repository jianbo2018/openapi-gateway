package com.fpay.openapi.gateway.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author jianbo
 * @Date 2021/6/24 8:36 下午
 * @Version 1.0
 * @Description <br/>
 * 聚合请求格式：
 * * uri:/aggregate/${type}
 * ${type}:聚合请求的类型，目前仅支持normal
 * request body type: raw-json
 */
@Data
public class AggregateRequestDTO implements Serializable {
    private RequestItem[] requestList;
    private long timeout;
}
