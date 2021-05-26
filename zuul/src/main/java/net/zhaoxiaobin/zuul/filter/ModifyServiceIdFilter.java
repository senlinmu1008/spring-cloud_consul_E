/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zhaoxb
 * @create 2019-12-24 15:21
 */
@Component
@Slf4j
public class ModifyServiceIdFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String modifyServiceId = request.getHeader("modifyServiceId");
        return modifyServiceId != null;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Object originalService = context.get(FilterConstants.SERVICE_ID_KEY);
        log.info("原serviceId:{}", originalService);
        String modifyServiceId = context.getRequest().getHeader("modifyServiceId");
        log.info("修改serviceId:{}", modifyServiceId);
        context.put(FilterConstants.SERVICE_ID_KEY, modifyServiceId);
        context.put(FilterConstants.REQUEST_URI_KEY, context.get(FilterConstants.REQUEST_URI_KEY).toString().replace("app-server", ""));
        return null;
    }
}