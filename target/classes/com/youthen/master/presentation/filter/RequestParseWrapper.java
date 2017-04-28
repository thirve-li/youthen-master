// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.filter;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

/**
 * 。
 * 
 * @author Administrator
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class RequestParseWrapper extends JakartaMultiPartRequest {

    @Override
    public void parse(final HttpServletRequest servletRequest, final String saveDir) throws IOException {
        final String url = servletRequest.getRequestURL().toString();
        System.out.println("------>url " + url);
        if (!url.contains("ueditor/jsp/controller.jsp") || url.contains("phoneInterface.do")
                || url.contains("updateHeadPic")) {
            super.parse(servletRequest, saveDir);
            System.out.println("------>do if " + url);
        }
        System.out.println("------>do my RequestParseWrapper");
    }

}
