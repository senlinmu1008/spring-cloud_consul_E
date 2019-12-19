# 应用server
* SpringCloud Edgware版
* SpringBoot 1.5.9.RELEASE
* JDK1.8
* 服务器 undertow
* 服务注册与发现 consul1.5.0

## yml配置
```yaml
server:
  port: 40100
spring:
  application:
    name: app-server
  cloud:
    consul:
      enabled: true
      host: 172.16.122.104
      port: 8500
      discovery:
        enabled: true
        instance-id: ${spring.application.name}-${spring.cloud.client.ipAddress}-${server.port}
      config: # config配置必须要在bootstrap的配置文件中，更早地被执行读取
        enabled: true
        format: YAML # 表示consul上面文件的格式 有四种 YAML PROPERTIES KEY-VALUE FILES
        data-key: configuration # 表示consul上面的KEY值(或者说文件的名字) 默认是data
```
***启动类需要添加注解`@EnableDiscoveryClient`***



