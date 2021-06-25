package com.fpay.openapi.gateway;

import com.fpay.openapi.gateway.context.FpayGatewayApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author jianbo
 * @Date 2021/6/22 2:09 下午
 * @Version 1.0
 * @Description <br/>
 */
@SpringBootApplication
public class OpenApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(OpenApiGatewayApplication.class);
        application.setApplicationContextClass(FpayGatewayApplicationContext.class);
        application.run(args);
    }
}
