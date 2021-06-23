package com.fpay.openapi.gateway.filter;

import com.fpay.openapi.gateway.filter.annotation.GatewayFilter;
import com.fpay.openapi.gateway.web.context.FpayGatewayApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @Author jianbo
 * @Date 2021/6/22 5:29 下午
 * @Version 1.0
 * @Description <br/>
 * 1. 根据annotation属性设置filterType
 * 2. 注册到GatewayFilterRegistry中，并根据filterOrder排序
 */
@Slf4j
@Component
public class GatewayFilterBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractGatewayFilter) {
            //1. 根据注解property设置filterType属性
            AbstractGatewayFilter filter = (AbstractGatewayFilter) bean;
            GatewayFilter annotation = filter.getClass().getAnnotation(GatewayFilter.class);
            String filterType = (String) AnnotationUtils.getValue(annotation, "filterType");
            checkFilterType(filterType);
            filter.setFilterType(filterType);
            //2. 将filter注册到registry中
            ((FpayGatewayApplicationContext) applicationContext).getFilterRegistry().registerGatewayFilter(filter);
        }
        return bean;
    }

    private void checkFilterType(String filterType) {
        if (filterType.equals("pre") || filterType.equals("route") || filterType.equals("post") || filterType.equals("error")) {
            return;
        }
        throw new IllegalStateException("GatewayFilter's type should be in [pre,route,post,error]");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
