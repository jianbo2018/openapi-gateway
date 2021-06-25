package com.fpay.openapi.gateway.filter;

import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;

/**
 * @Author jianbo
 * @Date 2021/6/25 12:29 上午
 * @Version 1.0
 * @Description <br/>
 */
public abstract class AbstractGatewayFilter implements Comparable<AbstractGatewayFilter> {

    public abstract int getOrder();

    public abstract void process();

    public void doFilter() {
        RequestFilterContext context = RequestFilterContext.getCurrentContext();
        FilterStatus status = context.getStatus();
        if (status == null || status == FilterStatus.ERROR || status == FilterStatus.SKIP) {
            return;
        }
        context.setStatus(FilterStatus.SUCCESS);
        try {
            process();
        } catch (Throwable throwable) {
            context.setStatus(FilterStatus.ERROR);
            context.setError(throwable);
        }
    }

    @Override
    public int compareTo(AbstractGatewayFilter o) {
        return this.getOrder() - o.getOrder();
    }
}
