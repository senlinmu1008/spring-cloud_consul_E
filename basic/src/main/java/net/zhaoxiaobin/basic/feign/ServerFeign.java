/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.basic.feign;

import net.zhaoxiaobin.basic.domain.request.Req_Entity;
import net.zhaoxiaobin.basic.domain.response.Resp_Entity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 20:03
 */
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