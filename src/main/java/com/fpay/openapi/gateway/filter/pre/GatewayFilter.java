package com.fpay.openapi.gateway.filter.pre;

import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;

/**
 * @Author jianbo
 * @Date 2021/6/25 12:29 上午
 * @Version 1.0
 * @Description <br/>
 */
public abstract class GatewayFilter implements Comparable<GatewayFilter> {
    private GatewayFilter next;

    public abstract int getOrder();

    public abstract void process();

    public void doFilter() {
        RequestFilterContext context = RequestFilterContext.getCurrentContext();
        FilterStatus status = context.getStatus();
        if (status == null || status == FilterStatus.ERROR || status == FilterStatus.SKIP) {
            return;
        }
        context.setStatus(FilterStatus.SUCCESS);
        process();
        if (next != null) {
            next.doFilter();
        }
    }

    @Override
    public int compareTo(GatewayFilter o) {
        return this.getOrder() - o.getOrder();
    }
}
