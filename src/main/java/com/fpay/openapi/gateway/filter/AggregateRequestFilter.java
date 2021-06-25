package com.fpay.openapi.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpay.openapi.gateway.netty.handler.RequestFilterContext;
import com.fpay.openapi.gateway.web.dto.AggregateRequestDTO;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * @Author jianbo
 * @Date 2021/6/24 6:12 下午
 * @Version 1.0
 * @Description <br/>
 * 负责处理聚合请求
 */
@Service
public class AggregateRequestFilter extends AbstractGatewayFilter {

    @Override
    public int getOrder() {
        return -1000;
    }

    @Override
    public void process() {
        RequestFilterContext context = RequestFilterContext.getCurrentContext();
        FullHttpRequest request = (FullHttpRequest) context.getHttpRequest();
        String uriString = request.uri();
        String regex = "/aggregate/.*";
        if (Pattern.matches(regex, uriString)) {
            //如果是聚合请求
            String json = request.content().toString(CharsetUtil.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                AggregateRequestDTO dtoReq = objectMapper.readValue(json, AggregateRequestDTO.class);
                context.setAggregateRequestDTO(dtoReq);
            } catch (JsonProcessingException e) {
                context.setError(e);
            }
        }
    }
}
