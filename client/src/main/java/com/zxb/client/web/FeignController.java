/**
 * Copyright (C), 2015-2019
 */
package com.zxb.client.web;

import com.alibaba.fastjson.JSON;
import com.zxb.basic.domain.response.Resp_Entity;
import com.zxb.basic.feign.ServerFeign;
import com.zxb.client.utils.ClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 20:13
 */
@RestController
@RequestMapping("/feign")
@Api(tags = {"Feign"})
public class FeignController {
    @Autowired
    private ServerFeign serverFeign;

    @ApiOperation(value = "postSimpleParam", notes = "简单类型参数传参")
    @PostMapping(value = "/post/simpleParam")
    public Resp_Entity postSimpleParam() {
        return serverFeign.postSimpleParam("postSimpleParam");
    }

    @ApiOperation(value = "getSimpleParam", notes = "简单类型参数传参")
    @GetMapping(value = "/get/simpleParam")
    public Resp_Entity getSimpleParam() {
        return serverFeign.getSimpleParam("getSimpleParam");
    }

    @ApiOperation(value = "postMutilSimpleParam", notes = "多个简单类型参数传参")
    @PostMapping(value = "/post/mutilSimpleParam")
    public Resp_Entity postMutilSimpleParam() {
        return serverFeign.postMutilSimpleParam("post", "mutilSimpleParam");
    }

    @ApiOperation(value = "postObject", notes = "对象传参")
    @PostMapping(value = "/post/object")
    public Resp_Entity postObject() {
        return serverFeign.postObject(ClientUtil.getRequestEntity());
    }

    @ApiOperation(value = "postJsonString", notes = "json字符串传参")
    @PostMapping(value = "/post/jsonString")
    public Resp_Entity postJsonString() {
        return serverFeign.postJsonString(JSON.toJSONString(ClientUtil.getRequestEntity()));
    }

    @ApiOperation(value = "postMixParam", notes = "混合类型参数传参")
    @PostMapping(value = "/post/mixParam")
    public Resp_Entity postMixParam() {
        return serverFeign.postMixParam("mixParam", ClientUtil.getRequestEntity());
    }

}