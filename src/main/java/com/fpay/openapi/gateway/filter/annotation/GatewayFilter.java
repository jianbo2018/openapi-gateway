package com.fpay.openapi.gateway.filter.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface GatewayFilter {
    /**
     * candidate value: pre,route,post,error
     */
    String filterType() default "route";
}
