package com.fpay.openapi.gateway.filter.pre;

import org.springframework.stereotype.Service;

/**
 * @Author jianbo
 * @Date 2021/6/25 3:23 上午
 * @Version 1.0
 * @Description <br/>
 * 根据uri定位想要访问的后端dubbo服务
 */
@Service
public class RoutePathFilter extends GatewayFilter {
    @Override
    public int getOrder() {
        return -99;
    }

    @Override
    public void process() {
        //todo: get service from dubbo.zk
    }
}
