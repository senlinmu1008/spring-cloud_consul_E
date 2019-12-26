/**
 * Copyright (C), 2015-2019
 */
package com.zxb.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhaoxb
 * @create 2019-12-24 10:07
 */
@Component
@Slf4j
public class ModifyUrlFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String modifyUri = request.getHeader("modifyUri");
        return modifyUri != null;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String uri = context.get(FilterConstants.REQUEST_URI_KEY).toString();
        log.info("原uri:{}", uri);
        String modifyUri = context.getRequest().getHeader("modifyUri");
        log.info("修改后的uri:{}", modifyUri);
        context.put(FilterConstants.REQUEST_URI_KEY, modifyUri);
        return null;
    }
}