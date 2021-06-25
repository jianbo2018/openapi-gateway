package com.fpay.openapi.gateway.filter;

import com.fpay.member.api.MemberService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.registry.dubbo.DubboRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author jianbo
 * @Date 2021/6/25 2:41 下午
 * @Version 1.0
 * @Description <br/>
 * 根据请求path，查询对应的dubbo服务。
 */
@Service
public class ServiceDiscoveryFilter extends AbstractGatewayFilter {

    @DubboReference
    private MemberService memberService;

    @Override
    public int getOrder() {
        //previous filter's order is -1000->AggregateRequestFilter
        return -990;
    }

    @Override
    public void process() {
        System.out.println(memberService.getById(1L));
    }
}
