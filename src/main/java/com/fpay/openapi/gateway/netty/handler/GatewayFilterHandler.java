package com.fpay.openapi.gateway.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpay.openapi.gateway.filter.enumm.FilterStatus;
import com.fpay.openapi.gateway.filter.pre.FilterRegistry;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @Author jianbo
 * @Date 2021/6/24 6:20 下午
 * @Version 1.0
 * @Description <br/>
 */
@Slf4j
@Service
@ChannelHandler.Sharable
public class GatewayFilterHandler extends AbstractBusinessHandler {
    @Autowired
    private FilterRegistry filterRegistry;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpRequest request = (FullHttpRequest) msg;
        request.retain();
        executor.execute(() -> {
            System.out.println(request.uri());
            System.out.printf("[%s]--[%s]%n", "PreFilterHandler.businessExecutor.execute(....)", Thread.currentThread().getName());
            RequestFilterContext context = RequestFilterContext.getCurrentContext();
            context.setHttpRequest(request);
            filterRegistry.doFilter();

            FilterStatus status = context.getStatus();
            DefaultFullHttpResponse response = null;
            switch (status) {
                case SUCCESS:
                    String respJson = context.getHttpResponseJson();
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                            Unpooled.wrappedBuffer(respJson.getBytes(StandardCharsets.UTF_8)));
                    break;
                case SKIP:
                case ERROR:
                    String errorJson = "";
                    try {
                        errorJson = new ObjectMapper().writeValueAsString(new ErrorMessage());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    //todo: 临时草稿
                    HttpResponseStatus httpResponseStatus = (HttpResponseStatus) context.get("httpResponseStatus");
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus,
                            Unpooled.wrappedBuffer(errorJson.getBytes(StandardCharsets.UTF_8)));
                    break;
                default:
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
            }
            response.headers().set(CONTENT_TYPE, "application/json");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.writeAndFlush(response);

        });

        ctx.fireChannelRead(msg);
    }
}
