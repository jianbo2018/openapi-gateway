package com.fpay.openapi.gateway.filter.core;

import com.fpay.openapi.gateway.context.FpayGatewayApplicationContext;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpRequest;

/**
 * @Author jianbo
 * @Date 2021/6/24 5:06 下午
 * @Version 1.0
 * @Description <br/>
 */
public class PreFilterChain  {

    public RequestFilterContext doFilter(RequestFilterContext request) {
        return null;
    }

}
