package com.youthen.master.presentation.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.SimpleAppMessage;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Results({@Result(name = SUCCESS, type = "redirect", location = "/login.jsp")})
@Controller
public class ExceptionRedirectAction implements SessionAware {

    private Map<String, Object> sessionMap;

    /**
     * 同一个账户登陆。
     * 
     * @return SUCCESS
     */
    @Action("concurrency")
    public String concurrency() {
        this.sessionMap.put("AUTHENTICATION_ERROR_MESSAGE", new SimpleAppMessage("ACT0002").getMesg());
        return SUCCESS;
    }

    /**
     * 认证失败 。
     * 
     * @return SUCCESS
     */
    @Action("authentication")
    public String authentication() {
        this.sessionMap.put("AUTHENTICATION_ERROR_MESSAGE", new SimpleAppMessage("ECO13035").getMesg());
        return SUCCESS;
    }

    /**
     * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
     */

    @Override
    public void setSession(final Map<String, Object> aSession) {
        this.sessionMap = aSession;
    }

}
