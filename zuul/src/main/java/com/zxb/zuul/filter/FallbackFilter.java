/**
 * Copyright (C), 2015-2019
 */
package com.zxb.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhaoxb
 * @create 2019-12-24 23:38
 */
@Component
@Slf4j
public class FallbackFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String fallback = request.getHeader("fallback");
        return fallback != null;
    }

    @Override
    @SneakyThrows
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        // 先设置终止路由
        context.setSendZuulResponse(false);

        // 第一种方式：设置错误响应码和错误信息
        context.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, "SC_FORBIDDEN");

        // 第二种方式：单独设置错误响应码和响应体
//        context.setResponseStatusCode(HttpServletResponse.SC_FORBIDDEN);
//        context.setResponseBody("SC_FORBIDDEN");
        return null;
    }
}