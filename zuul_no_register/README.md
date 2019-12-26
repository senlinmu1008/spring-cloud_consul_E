# zuul-无注册中心
没有注册中心管理服务，靠手动维护url和实例的映射关系
* 单实例
* 多实例

## 开启zuul
启动类添加注解`@EnableZuulProxy`和`@SpringCloudApplication`

## 单实例
通过一组`zuul.routes.<route>.path`与`zuul.routes.<route>.url`参数对的方式配置，比如：
```yaml
zuul:
  routes:
    # zuul根据path和url映射关系直接调用目标服务
    single-instance:
      path: /single-instance-service/**
      url: http://127.0.0.1:40200/
```
***根据path和url映射的服务请求不会使用Hystrix熔断机制和Ribbon负载均衡机制***

## 多实例
通过一组`zuul.routes.<route>.path`与`zuul.routes.<route>.serviceId`参数对的方式配置，比如：
```yaml
zuul:
  routes:
    # zuul根据path和serviceId(自定义，与应用名无关)映射关系找到具体实例，手动维护服务与实例的关系
    multi-instance:
      path: /multi-instance-service/**
      serviceId: multi-instance-consul-client
multi-instance-consul-client:
  ribbon:
    listOfServers: http://127.0.0.1:40200/,http://127.0.0.1:40201/
```
在一些情况下还需要禁用服务治理框架
```yaml
ribbon.eureka.enabled: false
```
```yaml
spring.cloud.consul.ribbon.enabled: false
```