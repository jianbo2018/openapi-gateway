package com.fpay.openapi.gateway.web.filter;

import com.fpay.openapi.gateway.filter.AbstractGatewayFilter;
import com.fpay.openapi.gateway.filter.annotation.GatewayFilter;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author jianbo
 * @Date 2021/6/22 5:11 下午
 * @Version 1.0
 * @Description <br/>
 */
@GatewayFilter(filterType = "pre")
@Slf4j
public class DemoFilter extends AbstractGatewayFilter {

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run(HttpRequest request) throws Throwable {
        log.info("request url is [{}]",request.uri());
        return "hello gateway filter!";
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
