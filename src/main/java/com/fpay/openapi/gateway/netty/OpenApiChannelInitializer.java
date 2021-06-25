package com.fpay.openapi.gateway.netty;

import com.fpay.openapi.gateway.netty.handler.GatewayFilterHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author jianbo
 * @Date 2021/6/22 3:06 下午
 * @Version 1.0
 * @Description <br/>
 */
@Component
public class OpenApiChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Autowired
    private GatewayFilterHandler gatewayFilterHandler;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("httpServerCode-handler", new HttpServerCodec())
                .addLast("httpObjectAggregator-handler", new HttpObjectAggregator(8192))
                .addLast("preFilter-handler", gatewayFilterHandler);
    }

}
