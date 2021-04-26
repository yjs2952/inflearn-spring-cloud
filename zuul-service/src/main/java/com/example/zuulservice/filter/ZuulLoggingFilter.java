package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";   // 사전 필터
    }

    @Override
    public int filterOrder() {
        return 1;   // 필터가 여러개일 경우 순서
    }

    @Override
    public boolean shouldFilter() {
        return true;    // 필터로 쓸지 안 쓸지 여부
    }

    @Override
    public Object run() throws ZuulException {  // 실제 필터 로직
        log.info("******************* printing logs: ");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("******************* printing logs: " + request.getRequestURI());
        return null;
    }
}
