package com.fpay.openapi.gateway.filter;

import com.fpay.openapi.gateway.context.FpayGatewayApplicationContext;
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
public class FilterChainPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private FpayGatewayApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //AbstractGatewayFilter，则注册到FilterRegistry
        if (bean instanceof AbstractGatewayFilter) {
            applicationContext.getBean(FilterRegistry.class).registerPreFilter((AbstractGatewayFilter) bean);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (FpayGatewayApplicationContext) applicationContext;
    }
}
