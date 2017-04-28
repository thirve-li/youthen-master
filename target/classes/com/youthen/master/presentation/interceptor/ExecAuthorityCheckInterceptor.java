package com.youthen.master.presentation.interceptor;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.ApplicationException;
import com.youthen.master.common.FunctionCdHandler;
import com.youthen.master.logic.auth.ExecAuthorityChecker;

public class ExecAuthorityCheckInterceptor extends AbstractInterceptor {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 5026013836690050290L;

    @Autowired
    private ExecAuthorityChecker checker;

    @Override
    public String intercept(final ActionInvocation aInvocation) throws Exception {

        final String functioncd = getFunctioncd(aInvocation);
        if (functioncd != null) {
            // this.checker.check(functioncd, SessionContext.getUser().getUserId());
        }
        return aInvocation.invoke();
    }

    /**
     * 权限Code
     * 
     * @param aInvocation Action
     * @return Functioncd
     */
    private String getFunctioncd(final ActionInvocation aInvocation) {

        Method method = null;
        final Class<?> clazz = aInvocation.getAction().getClass();
        final String methodName = aInvocation.getProxy().getMethod();
        try {
            method = clazz.getMethod(methodName);

        } catch (final NoSuchMethodException e) {
            throw new ApplicationException(e, "XCO00006", clazz.getName(), methodName);
        }

        String functioncd = null;
        ExecAuthority execAuthority =
                AnnotationUtils.getAnnotation(method, ExecAuthority.class);
        if (execAuthority == null) {
            execAuthority = AnnotationUtils.findAnnotation(aInvocation.getAction().getClass(), ExecAuthority.class);
        }
        if (execAuthority != null) {
            functioncd = execAuthority.functioncd();
        } else {
            if (FunctionCdHandler.class.isAssignableFrom(clazz)) {
                functioncd = ((FunctionCdHandler) aInvocation.getAction()).getFunctionCd();
            }
        }

        return functioncd;

    }

}
