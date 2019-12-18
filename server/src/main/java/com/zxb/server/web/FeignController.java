/**
 * Copyright (C), 2015-2019
 */
package com.zxb.server.web;

import com.alibaba.fastjson.JSON;
import com.zxb.basic.domain.request.Req_Entity;
import com.zxb.basic.domain.response.Resp_Entity;
import com.zxb.server.service.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author zhaoxb
 * @create 2019-12-17 22:48
 */
@RestController
@Slf4j
@RequestMapping("/feign")
@Api(tags = {"Feign"})
public class FeignController {
    @Autowired
    private Service service;

    @ApiOperation(value = "postSimpleParam", notes = "简单类型参数传参")
    @PostMapping(value = "/post/simpleParam")
    public Resp_Entity postSimpleParam(String value) {
        log.info("请求参数:{}", value);
        return service.service();
    }

    @ApiOperation(value = "getSimpleParam", notes = "简单类型参数传参")
    @GetMapping(value = "/get/simpleParam")
    public Resp_Entity getSimpleParam(@RequestParam("req") String value) {
        log.info("请求参数:{}", value);
        return service.service();
    }

    @ApiOperation(value = "postMutilSimpleParam", notes = "多个简单类型参数传参")
    @PostMapping(value = "/post/mutilSimpleParam")
    public Resp_Entity postMutilSimpleParam(String arg1, String arg2) {
        log.info("请求参数arg1:{},arg2:{}", arg1, arg2);
        return service.service();
    }

    @ApiOperation(value = "postObject", notes = "对象传参")
    @PostMapping(value = "/post/object")
    public Resp_Entity postObject(@RequestBody @Validated Req_Entity req) {
        log.info("请求对象:{}", JSON.toJSONString(req, true));
        return service.service();
    }

    @ApiOperation(value = "postMixParam", notes = "混合类型参数传参")
    @PostMapping(value = "/post/mixParam")
    public Resp_Entity postMixParam(String arg, @RequestBody @Validated Req_Entity req) {
        log.info("请求参数:{}", arg);
        log.info("请求对象:{}", JSON.toJSONString(req, true));
        return service.service();
    }
}