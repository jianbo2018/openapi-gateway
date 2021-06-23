# openapi-gateway
支付系统对外api网关

1. netty实现http协议，nio"适当"减少平均响应时间、明显减少响应时间过长或超时请求数
2. 模仿spring-zuul实现服务路由、服务过滤等功能。基于"openapi-gateway作为一个dubbo服务的消费者订阅所有dubbo服务"
3. 在2的基础上添加权限验证等网关常用功能
4. 网关层的断路器其实是一个通用的功能，所以应该作为一个公共模块被依赖进来。
   * 该公共模块计划基于dubbo（简单版）或hystrix（高级版）额外实现一个maven依赖，然后在openapi-gateway中引入该依赖