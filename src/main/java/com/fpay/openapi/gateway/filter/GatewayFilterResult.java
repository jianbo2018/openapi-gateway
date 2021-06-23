package com.fpay.openapi.gateway.filter;

import lombok.Data;

/**
 * @Author jianbo
 * @Date 2021/6/22 4:47 下午
 * @Version 1.0
 * @Description <br/>
 */
@Data
public final class GatewayFilterResult {
    private Object result;
    private Throwable error;
    private ExecutionStatus status;

    public GatewayFilterResult() {
        this.status = ExecutionStatus.DISABLED;
    }

    public GatewayFilterResult(ExecutionStatus status) {
        this.status = status;
    }

    public GatewayFilterResult(Object result, ExecutionStatus status) {
        this.result = result;
        this.status = status;
    }
}
