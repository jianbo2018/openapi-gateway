package com.fpay.openapi.gateway.context;

import com.fpay.openapi.gateway.filter.pre.FilterRegistry;
import com.fpay.openapi.gateway.netty.OpenApiChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @Author jianbo
 * @Date 2021/6/22 1:37 下午
 * @Version 1.0
 * @Description <br/>
 * 继承GenericApplicationContext,在`onRefresh()`阶段启动netty server
 * 1. 获取必要配置参数，如端口号等
 */
@Slf4j
public class FpayGatewayApplicationContext extends AnnotationConfigReactiveWebApplicationContext {

    @Autowired
    private FilterRegistry filterRegistry;

    private ServerBootstrap serverBootstrap;
    private int serverPort;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ChannelFuture channelFuture;
    @Override
    protected void onRefresh() throws BeansException {
        serverPort = getServerPort();
        super.onRefresh();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        OpenApiChannelInitializer channelInitializer = getBean(OpenApiChannelInitializer.class);
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                //loggingHandler's default log level is debug
                .handler(new LoggingHandler())
                .childHandler(channelInitializer);
    }

    @Override
    protected void finishRefresh() {
        channelFuture = serverBootstrap.bind(serverPort).addListener(future -> {
            log.info("server started on port [{}]", serverPort);
        });
        super.finishRefresh();

    }

    private int getServerPort() {
        String portString = getEnvironment().getProperty("server.port");
        if (StringUtils.isEmpty(portString)) {
            //server port default is 10000
            return 10000;
        }
        try {
            return Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("server.port should be a number, the current value is[" + portString + "]");
        }
    }

    public FilterRegistry getPreFilterRegistry() {
        return filterRegistry;
    }
}
