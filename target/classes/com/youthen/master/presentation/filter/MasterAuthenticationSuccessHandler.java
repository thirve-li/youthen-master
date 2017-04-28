package com.youthen.master.presentation.filter;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.common.security.MasterAuthenticatedUser;
import com.youthen.master.persistence.dao.OsAudittrailDao;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.LoginUserDto;

public class MasterAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private String passChangeRedirectUrl;

    @Resource
    LoginUserService loginUserService;

    @Resource
    KbnService kbnService;

    @Resource
    private OsAudittrailDao osAudittrailDao;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest aRequest, final HttpServletResponse aResponce,
            final Authentication aAuthentication) throws ServletException, IOException {
        try {
            final Object principal = aAuthentication.getPrincipal();
            if (principal instanceof MasterAuthenticatedUser) {
                final MasterAuthenticatedUser user = (MasterAuthenticatedUser) principal;

                final LoginUser loginUser = this.loginUserService.getUserByUserId(user.getUserId());
                final LoginUserDto userDto = new LoginUserDto();
                BeanUtils.copyAllNullableProperties(loginUser, userDto);
                aRequest.getSession().setAttribute("LOGIN_USER", userDto);

                if (user.getStatus().equalsIgnoreCase("HOLIDAY")) {// 休假回来登录后变为正常状态
                    final KbnDto kbnDto = this.kbnService.getKbn("USER_STATUS", "NORMAL");
                    final Kbn kbn = new Kbn();
                    BeanUtils.copyAllNullableProperties(kbnDto, kbn);
                    loginUser.setStatus(kbn);

                    final OsAudittrail osAudittrail = new OsAudittrail();
                    osAudittrail.setCreater(loginUser);// 操作者
                    osAudittrail.setBeOptedObjectId(loginUser.getUserId());// 变更对象
                    osAudittrail.setTableName(LoginUser.class.getName());// 用户
                    osAudittrail.setCompanyId(loginUser.getCompany().getId());// 公司
                    osAudittrail.setChangedContent("[项目名称：用户状态  原值：休假  新值：正常]"); // 变更内容
                    osAudittrail.setReason("用户休假回来登录后变为正常状态。");// 变更原因
                    osAudittrail.setObjectName(loginUser.getName());
                    osAudittrail.setActionName("休假后登录系统");
                    this.osAudittrailDao.insert(osAudittrail);

                }

                if (user.getStatus().equalsIgnoreCase("NEW_USER")) {
                    getRedirectStrategy().sendRedirect(aRequest, aResponce, this.passChangeRedirectUrl);
                    return;
                }
                if (user.getStatus().equalsIgnoreCase("RESET")) {
                    getRedirectStrategy().sendRedirect(aRequest, aResponce, this.passChangeRedirectUrl);
                    aRequest.getSession().setAttribute("reset", "Y");
                    return;
                }

                if (!user.getUserId().equalsIgnoreCase("admin")) {
                    if (this.loginUserService.pwdIsValid(user.getUserId())) {
                        getRedirectStrategy().sendRedirect(aRequest, aResponce, this.passChangeRedirectUrl);
                        aRequest.getSession().setAttribute("expire", "Y");
                        return;
                    }
                }

            }

            super.onAuthenticationSuccess(aRequest, aResponce, aAuthentication);
            return;
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getter for passChangeRedirectUrl.
     * 
     * @return passChangeRedirectUrl
     */
    public String getPassChangeRedirectUrl() {
        return this.passChangeRedirectUrl;
    }

    /**
     * setter for passChangeRedirectUrl.
     * 
     * @param aPassChangeRedirectUrl passChangeRedirectUrl
     */
    public void setPassChangeRedirectUrl(final String aPassChangeRedirectUrl) {
        this.passChangeRedirectUrl = aPassChangeRedirectUrl;
    }

}
