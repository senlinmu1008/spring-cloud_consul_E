/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.client.web;

import net.zhaoxiaobin.basic.domain.response.Resp_Entity;
import net.zhaoxiaobin.client.utils.ClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 22:32
 */
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