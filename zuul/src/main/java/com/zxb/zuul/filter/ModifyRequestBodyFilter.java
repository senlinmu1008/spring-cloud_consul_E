/**
 * Copyright (C), 2015-2019
 */
package com.zxb.zuul.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zxb.basic.http.ModifyRequestBodyWrapper;
import com.zxb.basic.utils.HttpUtil;
import com.zxb.basic.utils.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhaoxb
 * @create 2019-12-24 16:13
 */
@Component
@Slf4j
public class ModifyRequestBodyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 3;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        return MediaType.APPLICATION_XML_VALUE.equals(request.getContentType());
    }

    @Override
    @SneakyThrows
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        context.set("originalContentType", request.getContentType());

        // 读取请求体
        String originalRequestBody = ServletUtil.getBody(request);
        log.info("原请求体:{}", originalRequestBody);
        String jsonBody = JacksonUtil.xml2json(originalRequestBody);
        log.info("修改后请求体:{}", jsonBody);
        // 重新包装请求体和请求格式
        ModifyRequestBodyWrapper modifyRequestBodyWrapper = HttpUtil.modifyRequestBodyAndContentType(request, jsonBody, MediaType.APPLICATION_JSON_UTF8_VALUE);
        context.setRequest(modifyRequestBodyWrapper);

        return null;
    }
}