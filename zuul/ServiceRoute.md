# zuul(一)-面向服务路由
网关除了可以实现请求路由、负载均衡、熔断、服务聚合等功能外还可以将各个微服务的公共逻辑抽取出来统一在网关层实现，比如校验过滤、鉴权等操作。

## 开启zuul
启动类添加注解`@EnableZuulProxy`和`@SpringCloudApplication`

## 手动创建url和服务的映射关系
通过一组`zuul.routes.<route>.path`与`zuul.routes.<route>.serviceId`的配对方式配置
```yaml
zuul:
  routes:
    feign:
      path: /feign/**
      serviceId: app-client
      stripPrefix: false
    restTemplate:
      path: /template/**
      serviceId: app-client
      stripPrefix: false
```
stripPrefix表示是否跳过path路径的前缀部分

##### 路由匹配顺序
zuul使用路由规则匹配请求路径的时候是通过线性遍历的方式，匹配到第一个时即返回结束。如果是yaml文件则可以保证路由配置的顺序，让优先匹配的路径配置在前面。

##### 忽略表达式
对于一组符合表达式的路由请求不进行路由转发
```yaml
zuul:
  ignored-patterns: /feign/get/**
```


## 自动创建服务路由
默认使用服务名作为path路由请求的前缀，自动创建如下配置的路由规则
```yaml
zuul:
  routes:
    feign:
      path: /app-client/**
      serviceId: app-client
```
##### 禁止自动创建默认路由
```yaml
# 单独禁止
zuul:
  ignored-services: app-client
# 禁止所有
zuul:
  ignored-services: "*"
```
## 查看所有路由
访问`routes`端点可以获取当前所有的路由请求与服务的映射关系列表
```json
{
    "/feign/**": "app-client",
    "/template/**": "app-client",
    "/app-client/**": "app-client",
    "/app-server/**": "app-server",
    "/consul/**": "consul"
}
```

## cookie与请求头
zuul在转发请求时会把HTTP请求头的一些敏感信息过滤掉，包括`Cookie`、`Set-Cookie`、`Authorization`。为了防止在一些鉴权场景出现问题，可以如下配置：
```yaml
# 全局设置，将敏感信息列表置为空（不推荐）
zuul.sensitive-headers: 
# 对于某个路由将敏感信息列表置为空（推荐）
zuul.routes.<route>.sensitive-headers:
# 或者
zuul.routes.<route>.custom-sensitive-headers: true 
```

## 请求重定向问题
在一些场景中，zuul在转发请求后会重定向到内部具体一个web应用实例地址，原因是因为请求响应头中的Location字段指向了具体的服务地址，Host也指向了具体的服务实例的IP和端口。可以通过如下配置解决：
```yaml
zuul.addHostHeader: true
```

## Hystrix熔断机制和超时设置
设置Hystrix超时时间
```yaml
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds: 60000
```
设置ribbon调用超时
```yaml
ribbon:
  ReadTimeout: 30000
  ConnectTimeout: 1000
```
* 当ribbon超时大于Hystrix超时时间，如果发生超时则Hystrix触发熔断机制。
* 如果ribbon超时小于Hystrix超时时间，则会触发zuul的重试机制，重试失败后才会报错。

##### 禁用重试机制
```yaml
# 全局
zuul.retryable: false
# 指定路由关闭
zuul.routes.<route>.retryable: false
```

##### zuul饥饿加载ribbon客户端
只针对配置路由有效，对默认自动创建的路由无效。
```yaml
ribbon:
  eager-load:
    enabled: true
    clients:
      - app-client
      - app-server
```