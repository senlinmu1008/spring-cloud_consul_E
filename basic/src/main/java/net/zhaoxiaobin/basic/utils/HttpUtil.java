/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.basic.utils;

import net.zhaoxiaobin.basic.http.ModifyRequestBodyWrapper;
import net.zhaoxiaobin.basic.http.ModifyResponseBodyWrapper;
import jodd.servlet.ServletUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

/**
 * @author zhaoxb
 * @create 2019-10-05 19:34
 */
@Slf4j
public class HttpUtil {

    /**
     * 修改http请求体和contentType
     *
     * @return javax.servlet.http.HttpServletRequestWrapper
     * @author zhaoxb
     * @date 2019-10-05 14:51
     */
    public static ModifyRequestBodyWrapper modifyRequestBodyAndContentType(ServletRequest request, String modifyRequestBody) {
        return modifyRequestBodyAndContentType(request, modifyRequestBody, null);
    }

    public static ModifyRequestBodyWrapper modifyRequestBodyAndContentType(ServletRequest request, String modifyRequestBody, String contentType) {
        log.info("ContentType改为 -> {}", contentType);
        HttpServletRequest orginalRequest = (HttpServletRequest) request;

        return new ModifyRequestBodyWrapper(orginalRequest, modifyRequestBody, contentType);
    }


    /**
     * 获取新的响应对象用来获取原响应体
     *
     * @return javax.servlet.http.HttpServletResponseWrapper
     * @author zhaoxb
     * @date 2019-10-05 15:28
     */
    public static ModifyResponseBodyWrapper getOriginalResponseBody(ServletResponse response) {
        HttpServletResponse originalResponse = (HttpServletResponse) response;

        return new ModifyResponseBodyWrapper(originalResponse);
    }


    /**
     * 修改http请求体和响应体以及contentType
     *
     * @return void
     * @author zhaoxb
     * @date 2019-10-05 19:38
     */
    public static void modifyHttpData(ServletRequest originalRequest, ServletResponse originalResponse, FilterChain chain,
                                      Function<String, String> modifyRequestBodyFun, Function<String, String> modifyResponseBodyFun) throws IOException, ServletException {
        modifyHttpData(originalRequest, originalResponse, chain, modifyRequestBodyFun, modifyResponseBodyFun, null);
    }

    public static void modifyHttpData(ServletRequest request, ServletResponse response, FilterChain chain,
                                      Function<String, String> modifyRequestBodyFun, Function<String, String> modifyResponseBodyFun,
                                      String requestContentType) throws IOException, ServletException {
        HttpServletRequest originalRequest = (HttpServletRequest) request;
        HttpServletResponse originalResponse = (HttpServletResponse) response;

        String originalRequestBody = ServletUtil.readRequestBody(originalRequest); // 读取原请求体
        String modifyRequestBody = modifyRequestBodyFun.apply(originalRequestBody); // 修改请求体
        ModifyRequestBodyWrapper requestWrapper = modifyRequestBodyAndContentType(originalRequest, modifyRequestBody, requestContentType);
        modifyResponseBodyAndDoFilter(requestWrapper, originalResponse, chain, modifyResponseBodyFun);
    }


    /**
     * 修改响应体
     *
     * @return void
     * @author zhaoxb
     * @date 2019-10-05 18:55
     */
    public static void modifyResponseBodyAndDoFilter(ModifyRequestBodyWrapper modifyRequest, ServletResponse response, FilterChain chain, Function<String, String> modifyResponseBodyFun) throws IOException, ServletException {
        HttpServletResponse originalResponse = (HttpServletResponse) response;

        ModifyResponseBodyWrapper responseWrapper = getOriginalResponseBody(originalResponse);
        chain.doFilter(modifyRequest, responseWrapper);
        String originalResponseBody = responseWrapper.getResponseBody(); // 原响应体
        String modifyResponseBody = modifyResponseBodyFun.apply(originalResponseBody); // 修改后的响应体

        originalResponse.setContentType(modifyRequest.getOrginalRequest().getContentType()); // 与请求时保持一致
        byte[] responseData = modifyResponseBody.getBytes(responseWrapper.getCharacterEncoding()); // 编码与实际响应一致
        originalResponse.setContentLength(responseData.length);
        // 写出响应
        @Cleanup ServletOutputStream out = originalResponse.getOutputStream();
        out.write(responseData);

    }
}