# 应用client
* SpringCloud Edgware版
* SpringBoot 1.5.9.RELEASE
* JDK1.8
* 服务器 undertow
* 服务注册与发现 consul1.5.0

## yml配置
```yaml
server:
  port: 40200
spring:
  application:
    name: app-client
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

## Feign声明式调用
***启动类添加注解`@EnableFeignClients(basePackages = {"com.zxb.basic.feign"})`***

##### Feign接口
```Java
@FeignClient("app-server")
public interface ServerFeign {

    @PostMapping("/post/simpleParam")
    Resp_Entity postSimpleParam(@RequestParam("value") String value);// 通过@RequestParam指定形参名

    @GetMapping("/get/simpleParam")
    Resp_Entity getSimpleParam(@RequestParam("req") String req);

    @PostMapping("/post/mutilSimpleParam")
    Resp_Entity postMutilSimpleParam(@RequestParam("arg1") String arg1, @RequestParam("arg2") String arg2);

    @PostMapping(value = "/post/object", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Resp_Entity postJsonString(String request);

    @PostMapping(value = "/post/object")
    Resp_Entity postObject(Req_Entity req);

    @PostMapping(value = "/post/mixParam")
    Resp_Entity postMixParam(@RequestParam("arg") String arg, Req_Entity req);
}
```

##### 全局超时设置
```yaml
ribbon:
  ReadTimeout: 30000
  SocketTimeout: 1000
  ConnectTimeout: 1000
```

##### 对某个服务单独设置超时
```yaml
app-server:
  ribbon:
    ReadTimeout: 60000
    SocketTimeout: 2000
    ConnectTimeout: 2000
```

## RestTemplate
##### 配置Bean
```Java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectTimeout(1000);
    httpRequestFactory.setReadTimeout(30000); // 设置超时时间
    return new RestTemplate(httpRequestFactory);
}
```
##### 实现调用
```Java
@RestController
@RequestMapping("/template")
@Api(tags = {"template"})
public class TemplateController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String URL_PREFIXX = "http://app-server/";

    @ApiOperation(value = "postObject", notes = "对象传参")
    @PostMapping(value = "/post/object")
    public Resp_Entity postObject() {
        return restTemplate.postForObject(URL_PREFIXX.concat("post/object"), ClientUtil.getRequestEntity(), Resp_Entity.class);
    }
}
```

## Ribbon饥饿加载
***服务启动时即实例化客户端而非在实际调用时再实例化***
```yaml
ribbon:
  eager-load:
    enabled: true
    clients: app-server
```
多个client可以用逗号分隔。
##### 启动日志输出
```Java
2019-12-19 19:46:18,772 [INFO] [main] [com.netflix.loadbalancer.DynamicServerListLoadBalancer:150] [] DynamicServerListLoadBalancer for client app-server initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=app-server,current list of Servers=[192.168.31.58:40100],Load balancer stats=Zone stats: {unknown=[Zone:unknown;	Instance count:1;	Active connections count: 0;	Circuit breaker tripped count: 0;	Active connections per server: 0.0;]
},Server stats: [[Server:192.168.31.58:40100;	Zone:UNKNOWN;	Total Requests:0;	Successive connection failure:0;	Total blackout seconds:0;	Last connection made:Thu Jan 01 08:00:00 CST 1970;	First connection made: Thu Jan 01 08:00:00 CST 1970;	Active Connections:0;	total failure count in last (1000) msecs:0;	average resp time:0.0;	90 percentile resp time:0.0;	95 percentile resp time:0.0;	min resp time:0.0;	max resp time:0.0;	stddev resp time:0.0]
]}ServerList:ConsulServerList{serviceId='app-server', tag=null}
```