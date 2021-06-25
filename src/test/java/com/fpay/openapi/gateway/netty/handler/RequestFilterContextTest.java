package com.fpay.openapi.gateway.netty.handler;

import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RequestFilterContextTest {
    @Test
    public void testSimple() throws Exception {
        RequestFilterContext context = RequestFilterContext.getCurrentContext();
        System.out.printf("before: status:[%s]%n",context.getStatus());
        TimeUnit.SECONDS.sleep(1);
        Thread t1 = new Thread(() -> {
            context.setStatus(FilterStatus.ERROR);
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.printf("after: status:[%s]%n",context.getStatus().name());
    }
}