/**
 * Copyright (C), 2015-2019
 */
package com.zxb.zuul.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zxb.basic.utils.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhaoxb
 * @create 2019-12-24 22:21
 */
@Component
@Slf4j
public class ModifyResponseBodyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        // 必须比SEND_RESPONSE_FILTER_ORDER小
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return MediaType.APPLICATION_XML_VALUE.equals(context.get("originalContentType"));
    }

    @Override
    @SneakyThrows
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        log.info("响应码:[{}]", response.getStatus());

        // 读取原响应体并转xml
        String originalResponseBody = IoUtil.read(context.getResponseDataStream(), CharsetUtil.UTF_8);
        log.info("原响应体:{}", originalResponseBody);
        String responseBody = JacksonUtil.json2xml(originalResponseBody);
        log.info("转xml后的响应体:{}", responseBody);

        // 响应
        context.setResponseBody(responseBody);
        context.addZuulResponseHeader("Content-Type", context.get("originalContentType").toString().concat(";charset=UTF-8"));

        return null;
    }
}