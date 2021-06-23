package com.fpay.openapi.gateway.filter;

import io.netty.handler.codec.http.HttpRequest;

public interface IGatewayFilter {
    Object run(HttpRequest request) throws Throwable;
    boolean shouldFilter();
}
