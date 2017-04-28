package com.youthen.master.presentation.filter;

import java.io.IOException;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.common.security.SisqpFwPrincipal;
import com.youthen.framework.presentation.filter.SisqpFwUsernamePasswordAuthenticationFilter;
import com.youthen.master.common.security.MasterAuthenticatedUser;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.SystemConfigService;

public class MasterUsernamePasswordAuthenticationFilter extends SisqpFwUsernamePasswordAuthenticationFilter {

    @Resource
    LoginUserService loginUserService;

    @Resource
    KbnService kbnService;

    @Resource
    SystemConfigService systemConfigService;

    @Autowired
    LoginUserDao loginUserDao;

    private void setLocale(final HttpServletRequest aRequest) {
        final String local = aRequest.getParameter("local");
        if ("EN".equalsIgnoreCase(local)) {
            aRequest.getSession().setAttribute("WW_TRANS_I18N_LOCALE", Locale.US);
            LocaleContextHolder.setLocale(Locale.US);
        } else if ("BIG5".equalsIgnoreCase(local)) {
            LocaleContextHolder.setLocale(Locale.TAIWAN);
            aRequest.getSession().setAttribute("WW_TRANS_I18N_LOCALE", Locale.TAIWAN);
        } else {
            aRequest.getSession().setAttribute("WW_TRANS_I18N_LOCALE", Locale.CHINA);
            LocaleContextHolder.setLocale(Locale.CHINA);
        }
    }

    @SuppressWarnings("boxing")
    @Override
    protected void postProcessForSuccessfulAuthentication(final HttpServletRequest aRequest,
            final HttpServletResponse aResponse,
            final Authentication aAuthResult) throws IOException, ServletException {

        final SisqpFwPrincipal principal = new SisqpFwPrincipal(aAuthResult.getName());
        final MasterAuthenticatedUser user = (MasterAuthenticatedUser) aAuthResult.getPrincipal();

        setLocale(aRequest);
        // this.writeLoginLog(aRequest, principal, STATUS_NORMAL, RESULT_SUCCESS);
        final HttpSession session = aRequest.getSession(false);
        if (session != null) {
            session.removeAttribute(AUTHENTICATION_ERROR_MESSAGE_KEY);
            session.removeAttribute(COMPANY_CODE_KEY);
            session.removeAttribute(USERID_KEY);
            session.removeAttribute(SPRING_SECURITY_FORM_USERNAME_KEY);
            session.removeAttribute(SPRING_SECURITY_FORM_PASSWORD_KEY);
            session.removeAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY);
            session.removeAttribute(SPRING_SECURITY_LAST_USERNAME_KEY);

            // 去处密码错误次数
            final LoginUser aLoginUser = this.loginUserService.getUserByUserId(user.getUserId());
            try {
                this.loginUserService.excuteByHql("update LoginUser set passwordErrorCount = 0 where userId='"
                        + aLoginUser.getUserId() + "'");
            } catch (final Exception e) {
                throw new ServletException(e);
            }
            this.loginUserService.updateErrorPwdTimes(aLoginUser);
        }

    }

    @SuppressWarnings("boxing")
    @Override
    protected void postProcessForUnsuccessfulAuthentication(final HttpServletRequest aRequest,
            final HttpServletResponse aResponse,
            final AuthenticationException aFailed) throws IOException, ServletException {

        setLocale(aRequest);
        MasterAuthenticatedUser user = null;
        final Object extraInformation = aFailed.getExtraInformation();

        final StatusMessage statusMessage = this.getStatusMessage(aFailed);
        final HttpSession session = aRequest.getSession(false);

        if (extraInformation != null && extraInformation instanceof MasterAuthenticatedUser) {
            user = (MasterAuthenticatedUser) aFailed.getExtraInformation();
        }

        if (user == null) {// 用户名不存在
            session.setAttribute(AUTHENTICATION_ERROR_MESSAGE_KEY, statusMessage.getMessage());
        } else {

            final SisqpFwPrincipal principal = new SisqpFwPrincipal(aFailed.getAuthentication().getName());
            final LoginUser loginUser = this.loginUserService.getUserByUserId(user.getUserId());

            if (STATUS_PASSWORD_UNMATCH.equals(statusMessage.getStatus())) {

                this.setSecurityContext(aFailed);
                if (!user.getUserId().equalsIgnoreCase("admin")) {
                    this.loginUserService.lockUser(user.getUserId());
                }
            }

            this.writeLoginLog(aRequest, principal, statusMessage.getStatus(), RESULT_FAIL);

            if (session != null) {

                if (loginUser.getStatus().getCode().equalsIgnoreCase("DELETED")) {// 失效用户
                    final FieldSupportedMessage errorMessage = new FieldSupportedMessage("EFW56004");
                    session.setAttribute(AUTHENTICATION_ERROR_MESSAGE_KEY, errorMessage.getMesg());
                } else {
                    session.setAttribute(AUTHENTICATION_ERROR_MESSAGE_KEY, statusMessage.getMessage());

                }
                session.setAttribute(COMPANY_CODE_KEY, principal.getCompanyCode());
                session.setAttribute(USERID_KEY, principal.getUserId());

            }
        }
    }

    /**
     * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    protected void preProcessForUnsuccessfulAuthentication(final HttpServletRequest aRequest,
            final HttpServletResponse aResponse,
            final AuthenticationException aFailed) throws IOException, ServletException {
    }

}
