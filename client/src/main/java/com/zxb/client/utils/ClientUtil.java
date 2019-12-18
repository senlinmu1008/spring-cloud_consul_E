/**
 * Copyright (C), 2015-2019
 */
package com.zxb.client.utils;

import com.zxb.basic.domain.request.Req_Detail;
import com.zxb.basic.domain.request.Req_Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 21:13
 */
public class ClientUtil {
    public static Req_Entity getRequestEntity() {
        List<Req_Detail> details = new ArrayList<>();
        details.add(new Req_Detail().setSchool("浙江大学").setZipCode("310000"));
        details.add(new Req_Detail().setSchool("浙江海洋大学").setZipCode("316000"));

        Req_Entity reqEntity = new Req_Entity()
                .setUserName("zhaoxb")
                .setTelPhone("13485527323")
                .setOrgId("310000000")
                .setIdNo("110101199003079833")
                .setDistrictId("310115")
                .setBirthDay("19900307")
                .setDetails(details);
        return reqEntity;
    }
}