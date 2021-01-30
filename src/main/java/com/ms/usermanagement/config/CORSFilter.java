package com.ms.usermanagement.config;


import com.ms.usermanagement.util.CommonUtil;
import com.ms.usermanagement.util.Logger4j;
import com.ms.usermanagement.util.MacAddress;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {

    public CORSFilter() {
        Logger4j.getLogger().info("CORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Logger4j.getLogger()
                .info("IP:::" + request.getRemoteAddr() + "##MAC:::" + MacAddress.getMac(request.getRemoteAddr())
                        + "##DATE:::" + CommonUtil.getCurrentTimestamp().toString() + "##SERVICE:::"
                        + request.getRequestURI() + "##METHOD:::" + request.getMethod());

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, x-requested-with , Authorization , ");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

}
