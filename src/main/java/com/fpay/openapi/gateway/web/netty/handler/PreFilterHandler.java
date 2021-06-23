package com.fpay.openapi.gateway.web.netty.handler;

import com.fpay.openapi.gateway.filter.AbstractGatewayFilter;
import com.fpay.openapi.gateway.filter.GatewayFilterResult;
import com.fpay.openapi.gateway.web.context.FpayGatewayApplicationContext;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.TreeSet;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @Author jianbo
 * @Date 2021/6/22 10:13 下午
 * @Version 1.0
 * @Description <br/>
 */
@Slf4j
@Component
public class PreFilterHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private FpayGatewayApplicationContext context;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest request = (HttpRequest) msg;
        TreeSet<AbstractGatewayFilter> preFilterSet = context.getFilterRegistry().getPreFilter();
        preFilterSet.forEach(filter -> {
            GatewayFilterResult result = filter.runFilter(request);
            log.info("pre result:[{}]", result.getResult());
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.copiedBuffer(((String) result.getResult()).getBytes(StandardCharsets.UTF_8)));
            response.headers().set(CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.writeAndFlush(response);
        });
        //release for test. when test with route,post,error filter, we should fire msg to the next handler
        ReferenceCountUtil.release(msg);
//        ctx.fireChannelRead(msg);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (FpayGatewayApplicationContext) applicationContext;
    }
}
