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