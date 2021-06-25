package com.fpay.openapi.gateway.filter;

import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;
import org.springframework.stereotype.Component;

import java.util.TreeSet;

/**
 * @Author jianbo
 * @Date 2021/6/25 12:28 上午
 * @Version 1.0
 * @Description <br/>
 */
@Component
public class FilterRegistry {
    private final TreeSet<AbstractGatewayFilter> filterChain;

    public FilterRegistry() {
        this.filterChain = new TreeSet<>();
    }

    public void registerPreFilter(AbstractGatewayFilter filter) {
        filterChain.add(filter);
    }

    public TreeSet<AbstractGatewayFilter> getFilterChain() {
        return filterChain;
    }

    public void doFilter() {
        RequestFilterContext.getCurrentContext().setStatus(FilterStatus.SUCCESS);
        for (AbstractGatewayFilter gatewayFilter : filterChain) {
            gatewayFilter.doFilter();
        }
    }
}
