package com.eshop.app.services.auth;

import com.eshop.app.common.constants.AppConstants;
import com.eshop.app.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class GenericFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String correlationId = httpRequest.getHeader(AppConstants.REQUEST_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put(AppConstants.REQUEST_ID, correlationId);
        //MDC.put(AppConstants.ACTION_BY, ((HttpServletRequest) servletRequest).getRequestURI());
        //MDC.put(CommConstants.ACTION_TYPE, ((HttpServletRequest) servletRequest).getMethod());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
