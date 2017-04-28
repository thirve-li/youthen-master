package com.youthen.master.presentation.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;

public class UeditorFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res,
            final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final String url = request.getRequestURI();
        if (decideURI(url)) {
            chain.doFilter(new StrutsRequestWrapper((HttpServletRequest) req), res);
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean decideURI(final String url) {

        if (url.contains("ueditor/jsp/controller.jsp")) {
            return true;
        }
        return false;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */

    @Override
    public void destroy() {
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */

    @Override
    public void init(final FilterConfig aArg0) throws ServletException {
    }
}
