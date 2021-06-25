package com.fpay.openapi.gateway.filter;

import com.fpay.openapi.gateway.context.FpayGatewayApplicationContext;
import com.fpay.openapi.gateway.filter.pre.GatewayFilter;
import com.fpay.openapi.gateway.filter.pre.FilterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author jianbo
 * @Date 2021/6/25 1:43 上午
 * @Version 1.0
 * @Description <br/>
 */
@Component
public class PreFilterChainPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private FpayGatewayApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //如果是一个PreFilter，则注册到PreFilterRegistry
        if (bean instanceof GatewayFilter) {
            applicationContext.getBean(FilterRegistry.class).registerPreFilter((GatewayFilter) bean);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (FpayGatewayApplicationContext) applicationContext;
    }
}
