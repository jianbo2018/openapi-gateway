package com.fpay.openapi.gateway.web.dto;

import lombok.Data;

/**
 * @Author jianbo
 * @Date 2021/6/24 8:49 下午
 * @Version 1.0
 * @Description <br/>
 */
@Data
public class RequestItem<T> {
    private String path;
    private T requestBody;
}
