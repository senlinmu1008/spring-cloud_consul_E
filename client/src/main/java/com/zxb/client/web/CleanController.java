/**
 * Copyright (C), 2015-2019
 */
package com.zxb.client.web;

import com.alibaba.fastjson.JSON;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.Check;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhaoxb
 * @create 2019-12-26 23:06
 */
@RestController
@Api(tags = {"服务清理"})
@Slf4j
public class CleanController {
    @Autowired
    private ConsulClient consulClient;

    @DeleteMapping("/clear")
    public List clear(@RequestParam(required = false) String clearServiceName) {
        List<String> deleteList = new ArrayList<>();

        Map<String, Check> checkMap = consulClient.getAgentChecks().getValue();
        log.info("服务列表:\r\n{}", JSON.toJSONString(checkMap, true));
        checkMap.forEach((k, v) -> {
            if(v.getStatus() == Check.CheckStatus.PASSING) {
                return;
            }
            if(v.getServiceName().equals(clearServiceName) || clearServiceName == null) {
                log.info("删除实例：{}", v.getServiceId());
                consulClient.agentServiceDeregister(v.getServiceId());
                deleteList.add(v.getServiceId());
            }
        });

        return deleteList;
    }
}