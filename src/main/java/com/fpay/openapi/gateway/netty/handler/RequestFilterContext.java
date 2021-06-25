package com.fpay.openapi.gateway.netty.handler;


import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.web.dto.AggregateRequestDTO;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.concurrent.FastThreadLocal;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author jianbo
 * @Date 2021/6/24 5:40 下午
 * @Version 1.0
 * @Description <br/>
 */
public class RequestFilterContext extends ConcurrentHashMap<String,Object> {
    private static final Class<RequestFilterContext> clazz = RequestFilterContext.class;

    private static final FastThreadLocal<RequestFilterContext> threadLocal =
            new FastThreadLocal<RequestFilterContext>() {
                @Override
                protected RequestFilterContext initialValue() throws Exception {
                    return clazz.newInstance();
                }
            };

    public static RequestFilterContext getCurrentContext() {
        return threadLocal.get();
    }

    public void setHttpRequest(HttpRequest request) {
        putIfAbsent("httpRequest", request);
    }

    public HttpRequest getHttpRequest() {
        return (HttpRequest) get("httpRequest");
    }

    public void setError(Throwable e) {
        putIfAbsent("error", e);
    }

    public Throwable getError() {
        return (Throwable) get("error");
    }

    public void setStatus(FilterStatus status) {
        put("filterStatus", status);
    }

    public FilterStatus getStatus() {
        return (FilterStatus) get("filterStatus");
    }

    public void setAggregateRequestDTO(AggregateRequestDTO requestDTO) {
        put("aggregateRequestDTO", requestDTO);
    }

    public AggregateRequestDTO getAggregateRequestDTO() {
        return (AggregateRequestDTO) get("aggregateRequestDTO");
    }

    public void setHttpResponseJson(String response) {
        put("httpResponse", response);
    }

    public String getHttpResponseJson() {
        return (String) get("httpResponse");
    }
}
