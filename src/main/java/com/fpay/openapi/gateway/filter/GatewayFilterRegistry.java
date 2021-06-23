package com.fpay.openapi.gateway.filter;

import java.util.TreeSet;

/**
 * @Author jianbo
 * @Date 2021/6/22 6:05 下午
 * @Version 1.0
 * @Description <br/>
 */
public class GatewayFilterRegistry {
    private final TreeSet<AbstractGatewayFilter> preFilter = new TreeSet<>();
    private final TreeSet<AbstractGatewayFilter> routeFilter = new TreeSet<>();
    private final TreeSet<AbstractGatewayFilter> postFilter = new TreeSet<>();
    private final TreeSet<AbstractGatewayFilter> errorFilter = new TreeSet<>();

    public void registerGatewayFilter(AbstractGatewayFilter filter) {
        if (filter.getFilterType().equals("pre")) {
            preFilter.add(filter);
        } else if (filter.getFilterType().equals("route")) {
            routeFilter.add(filter);
        } else if (filter.getFilterType().equals("post")) {
            postFilter.add(filter);
        } else if (filter.getFilterType().equals("error")) {
            errorFilter.add(filter);
        } else {
            throw new IllegalArgumentException("filterType must in [pre,route,post,error]");
        }
    }

    public TreeSet<AbstractGatewayFilter> getPreFilter() {
        return preFilter;
    }

    public TreeSet<AbstractGatewayFilter> getRouteFilter() {
        return routeFilter;
    }

    public TreeSet<AbstractGatewayFilter> getPostFilter() {
        return postFilter;
    }

    public TreeSet<AbstractGatewayFilter> getErrorFilter() {
        return errorFilter;
    }
}
