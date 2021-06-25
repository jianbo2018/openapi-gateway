package com.fpay.openapi.gateway.netty.handler;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author jianbo
 * @Date 2021/6/24 5:50 下午
 * @Version 1.0
 * @Description <br/>
 */
@Data
public class ErrorMessage {
    private LocalDateTime timestamp;
    //http response code: 404,500等
    private int status;
    //如：Internal Server Error
    private String error;
    //如：No message available
    private String message;
    //发生异常的请求路径，如;/api/book/1
    private String path;
}
