package com.youthen.master.presentation.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.presentation.filter.SisqpFwUsernamePasswordAuthenticationFilter;
import com.youthen.framework.util.logging.LoggingUtils;
import com.youthen.master.common.security.MasterAuthenticatedUser;

public class AccessLoggingInterceptor extends AbstractInterceptor {

    /**
     * serial version uid
     */
    private static final long serialVersionUID = 2359005534591272812L;

    private static final String MDC_KEY_SYSTEM_CD = "systemCd";

    private static final String MDC_KEY_OPERATION_KAISHA_CD = "operationKaishaCd";

    private static final String MDC_KEY_OPERATION_COMPANY_NAME = "operationKaishaNm";

    private static final String MDC_KEY_OPERATION_USER_ID = "operationUserId";

    private static final String MDC_KEY_OPERATION_USER_NM = "operationUserNm";

    private static final String MDC_KEY_EVENT_ID = "eventId";

    private final List<String> ignoreList = new ArrayList<String>();

    private final Log accessLog = LogFactory.getLog("access");

    /**
     */
    public AccessLoggingInterceptor() {
        this.ignoreList.add(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
        this.ignoreList.add(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        this.ignoreList.add(SisqpFwUsernamePasswordAuthenticationFilter.AUTHENTICATION_ERROR_MESSAGE_KEY);
        this.ignoreList.add(SisqpFwUsernamePasswordAuthenticationFilter.COMPANY_CODE_KEY);
        this.ignoreList.add(SisqpFwUsernamePasswordAuthenticationFilter.USERID_KEY);
        this.ignoreList.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
        this.ignoreList.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        this.ignoreList.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY);
    }

    public void setIgnoreKeys(final String aIgnoreKeys) {
        this.ignoreList.addAll(TextParseUtil.commaDelimitedStringToSet(aIgnoreKeys));
    }

    @Override
    /**
     * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    public String intercept(final ActionInvocation aInvocation) throws Exception {

        final MasterAuthenticatedUser user = (MasterAuthenticatedUser) SessionContext.getUser();

        String companyCode = "";
        String companyName = "";
        String userId = "";
        String userNm = "";

        if (user != null) {
            companyCode = user.getCompanyCode();
            companyName = user.getCompanyName();
            userId = user.getUserId();
            userNm = user.getUsernm();
        }
        final String nameSpace = aInvocation.getProxy().getNamespace();
        final String actionName = aInvocation.getProxy().getActionName();
        final String eventId = nameSpace + "/" + actionName;

        MDC.put(MDC_KEY_SYSTEM_CD, "youthen");
        MDC.put(MDC_KEY_OPERATION_KAISHA_CD, companyCode);
        MDC.put(MDC_KEY_OPERATION_COMPANY_NAME, companyName);
        MDC.put(MDC_KEY_OPERATION_USER_ID, userId);
        MDC.put(MDC_KEY_OPERATION_USER_NM, userNm);
        MDC.put(MDC_KEY_EVENT_ID, eventId);

        if (this.accessLog.isInfoEnabled() && user != null) {
            final ActionContext actionContext = aInvocation.getInvocationContext();
            final Map<String, Object> requestMap = actionContext.getParameters();
            final String requestParameters = this.getKeyValueString(requestMap);
            final Map<String, Object> sessionMap = actionContext.getSession();
            final String sessionParameters = this.getKeyValueString(sessionMap);

            this.accessLog.info(new FieldSupportedMessage("IFW55001").format(requestParameters, sessionParameters));
        }

        final long startTime = System.currentTimeMillis();

        final String result = aInvocation.invoke();

        final long executionTime = System.currentTimeMillis() - startTime;

        if (this.accessLog.isInfoEnabled() && user != null) {
            this.accessLog.info(new FieldSupportedMessage("IFW55002").format(result, String.valueOf(executionTime)));
        }

        return result;
    }

    /**
     */
    private String getKeyValueString(final Map<String, Object> aKeyValueMap) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (!aKeyValueMap.isEmpty()) {
            for (final Entry<String, Object> entry : aKeyValueMap.entrySet()) {
                if (this.ignoreList.contains(entry.getKey())) {
                    continue;
                }
                stringBuilder.append("#{");
                stringBuilder.append(entry.getKey());
                stringBuilder.append("}=");
                stringBuilder.append(LoggingUtils.getStringExpression(entry.getValue()));
                stringBuilder.append(LoggingUtils.MESSAGE_DELIMITER);
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.delete(stringBuilder.length() - LoggingUtils.MESSAGE_DELIMITER.length(), stringBuilder
                        .length());
            }
        }

        return stringBuilder.toString();
    }
}
