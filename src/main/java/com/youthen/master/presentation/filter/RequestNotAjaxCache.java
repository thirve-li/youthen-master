package com.youthen.master.presentation.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import com.youthen.framework.common.security.AjaxRequestDetector;

public class RequestNotAjaxCache extends HttpSessionRequestCache {

    private AjaxRequestDetector ajaxRequestDetector = new AjaxRequestDetector();

    /**
     * @see org.springframework.security.web.savedrequest.HttpSessionRequestCache#saveRequest(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */

    @Override
    public void saveRequest(final HttpServletRequest aRequest, final HttpServletResponse aResponse) {
        if (!this.ajaxRequestDetector.isAjaxRequest(aRequest)) {
            // super.saveRequest(aRequest, aResponse);
        }
    }

    /**
     * setter for ajaxRequestDetector.
     * 
     * @param aAjaxRequestDetector ajaxRequestDetector
     */
    public void setAjaxRequestDetector(final AjaxRequestDetector aAjaxRequestDetector) {
        this.ajaxRequestDetector = aAjaxRequestDetector;
    }

}
