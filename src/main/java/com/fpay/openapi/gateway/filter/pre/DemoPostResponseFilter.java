package com.fpay.openapi.gateway.filter.pre;

import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;
import org.springframework.stereotype.Service;

/**
 * @Author jianbo
 * @Date 2021/6/25 4:17 上午
 * @Version 1.0
 * @Description <br/>
 */
@Service
public class DemoPostResponseFilter extends GatewayFilter {
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void process() {
        System.out.printf("[%s]--[%s]%n", "AggregateRequestFilter.process()", Thread.currentThread().getName());
        RequestFilterContext context = RequestFilterContext.getCurrentContext();
        context.setStatus(FilterStatus.SUCCESS);
        context.put("httpResponse", "{\"orderId\":12345,\"price\":12.50}");
    }
}
