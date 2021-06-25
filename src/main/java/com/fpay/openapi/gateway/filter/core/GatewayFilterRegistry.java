package com.fpay.openapi.gateway.filter.core;

/**
 * @Author jianbo
 * @Date 2021/6/24 4:46 下午
 * @Version 1.0
 * @Description <br/>
 */
public class GatewayFilterRegistry {


    private final PreFilterChain preFilterChain = new PreFilterChain();
    private final RouteFilterChain routeFilterChain = new RouteFilterChain();
    private final PostFilterChain postFilterChain = new PostFilterChain();

    public PreFilterChain getPreFilterChain() {
        return preFilterChain;
    }

    public RouteFilterChain getRouteFilterChain() {
        return routeFilterChain;
    }

    public PostFilterChain getPostFilterChain() {
        return postFilterChain;
    }
}
