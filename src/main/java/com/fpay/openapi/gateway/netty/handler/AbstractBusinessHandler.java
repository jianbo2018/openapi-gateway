package com.fpay.openapi.gateway.netty.handler;

import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.*;

/**
 * @Author jianbo
 * @Date 2021/6/24 6:21 下午
 * @Version 1.0
 * @Description <br/>
 */
public class AbstractBusinessHandler extends ChannelInboundHandlerAdapter {
    protected final static ExecutorService executor = createExecutorByRuntime();
    private static ExecutorService createExecutorByRuntime() {
        //suppose ioTimeCost/localProgramTimeCost = 10
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * (1 + 10);
        return new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
