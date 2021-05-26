/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.server.service.impl;

import net.zhaoxiaobin.basic.domain.ErrorCode;
import net.zhaoxiaobin.basic.domain.response.Resp_Detail;
import net.zhaoxiaobin.basic.domain.response.Resp_Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 11:08
 */
@Service
@Slf4j
public class ServiceImpl implements net.zhaoxiaobin.server.service.Service {

    @Override
    public Resp_Entity service() {
        List<Resp_Detail> detailList = IntStream.range(1, 6).mapToObj(i -> new Resp_Detail().setOrgId(i + "").setOrgName("机构名称" + i)).collect(Collectors.toList());
        Resp_Entity respEntity = new Resp_Entity()
                .setReturnCode(ErrorCode.SUCCESS.getValue())
                .setReturnMsg(ErrorCode.SUCCESS.getDesc())
                .setDetails(detailList);

        return respEntity;
    }
}