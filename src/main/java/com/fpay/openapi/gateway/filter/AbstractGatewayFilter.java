package com.fpay.openapi.gateway.filter;

import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author jianbo
 * @Date 2021/6/22 4:25 下午
 * @Version 1.0
 * @Description <br/>
 */
@Slf4j
public abstract class AbstractGatewayFilter implements IGatewayFilter, Comparable<AbstractGatewayFilter> {
    /**
     * 模仿zuul
     * filter 类型：
     * * pre
     * * route
     * * post
     * * error
     *
     * @return
     * @Deprecated: 改为使用注解设置filterType，这样的代码更易读
     */
//    public abstract String filterType();
    private String filterType;

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    /**
     * filter在各自范围内的执行顺序优先级，范围Integer.MIN_VALUE ~ Integer.MAX_VALUE
     * 数字越大，优先级越高
     *
     * @return
     */
    public abstract int filterOrder();

    /**
     * 是否禁用该filter，默认启用，可以通过重写该方法禁用filter
     * filter的disabled和shouldFilter两个状态的区别是：
     * 当为disabled状态时，`runFilter()`方法直接返回一个状态为DISABLED的GatewayFilterResult
     * 而当为"非shouldFilter"时，`runFilter()`返回的GatewayFilterResult的状态为Skipped
     * <p>
     * 这两种状态给到上游调用方时，可能会因为这两种状态的不同而执行不同的逻辑
     *
     * @return
     */
    public boolean isFilterDisabled() {
        return false;
    }

    @Override
    public int compareTo(AbstractGatewayFilter o) {
        return Integer.compare(this.filterOrder(), o.filterOrder());
    }

    public GatewayFilterResult runFilter(HttpRequest request) {
        GatewayFilterResult result = new GatewayFilterResult();
        if (!isFilterDisabled()) {
            if (shouldFilter()) {
                try {
                    Object res = run(request);
                    result.setResult(res);
                    result.setStatus(ExecutionStatus.SUCCESS);
                } catch (Throwable e) {
                    result.setError(e);
                    result.setStatus(ExecutionStatus.FAILED);
                }
            } else {
                result.setStatus(ExecutionStatus.SKIPPED);
            }
        }
        return result;
    }
}
