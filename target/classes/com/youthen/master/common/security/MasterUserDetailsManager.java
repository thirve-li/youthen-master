package com.youthen.master.common.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import com.youthen.framework.common.SimpleAppMessage;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.SystemError;
import com.youthen.framework.common.beans.BeanDefineInfo;
import com.youthen.framework.common.exception.ApplicationException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.security.SisqpFwPrincipal;
import com.youthen.master.service.PwdHistoryService;

public class MasterUserDetailsManager extends JdbcUserDetailsManager {

    protected static final String C_COL_USERID = "USER_ID";
    protected static final String C_COL_USERNM = "NAME";
    protected static final String C_COL_PASSWORD = "PASSWORD";
    protected static final String C_COL_EMAIL = "EMAIL";
    protected static final String C_COL_DEPTNM = "DEPTNM";
    protected static final String C_COL_COMPANYCD = "COMPANY_CODE";
    protected static final String C_COL_COMPANY_ID = "COMPANY_ID";
    protected static final String C_COL_DEPARTMENT_ID = "DEPT_ID";

    protected static final String C_COL_STATUS = "STATUS";

    protected static final String C_COL_PWD_ERROR_COUNT = "PWD_ERROR_COUNT";

    protected static final String C_COL_ROLE_CODE = "ROLE_CODE";

    private static final Log LOG = LogFactory.getLog(MasterUserDetailsManager.class);

    private String defaultCompanyQuery;

    private String dataSourcePrefix = "ds";

    private String companyCodeForLogin = "Login";

    MasterAuthenticatedUser masterAuthenticatedUser;

    @Resource
    PwdHistoryService pwdHistoryService;

    @Override
    protected List<UserDetails> loadUsersByUsername(final String aUsername) {
        try {
            // 只有唯一服务器能启动
            // if (!checkServer()) {
            // throw new RecoverableDataAccessException("该服务器不能使用");
            // }

            final SisqpFwPrincipal principal = this.createPrincipal(aUsername);

            return this.createJdbcTemplate(this.findLoginDataSource()).query(
                    this.getUsersByUsernameQuery(),
                    new String[] {principal.getUserId()},
                    new RowMapper<UserDetails>() {

                        public UserDetails mapRow(final ResultSet aRs, final int aRowNum) throws SQLException {

                            final String password = aRs.getString(C_COL_PASSWORD);
                            final boolean enabled = true;
                            String accountStatus = aRs.getString(C_COL_STATUS);
                            final Long comId = aRs.getLong(C_COL_COMPANY_ID);
                            final Long deptId = aRs.getLong(C_COL_DEPARTMENT_ID);
                            final int pwdErrorCount = aRs.getInt(C_COL_PWD_ERROR_COUNT);

                            // 帐户是否正常,未锁定
                            boolean IsAccountOk = false;

                            if (accountStatus.equalsIgnoreCase("NORMAL") || accountStatus.equalsIgnoreCase("NEW_USER")
                                    || accountStatus.equalsIgnoreCase("RESET")
                                    || accountStatus.equalsIgnoreCase("HOLIDAY")) {
                                IsAccountOk = true;
                            }

                            // 密码是否过期
                            try {
                                if (MasterUserDetailsManager.this.pwdHistoryService.validatePwdChangeDay(aUsername)) {
                                    accountStatus = "NEW_USER";
                                }
                            } catch (final ObjectNotFoundException e) {
                                e.printStackTrace();
                            }

                            final String mailAddress = aRs.getString(C_COL_EMAIL);
                            final String deptnm = aRs.getString(C_COL_DEPTNM);
                            final String userName = aRs.getString(C_COL_USERNM);
                            final String companyCode = aRs.getString(C_COL_COMPANYCD);
                            final String status = aRs.getString(C_COL_STATUS);

                            MasterUserDetailsManager.this.masterAuthenticatedUser = new MasterAuthenticatedUser(
                                    principal,
                                    password,
                                    enabled,// 用户激活
                                    true, // 帐户未过期
                                    true,// 证书\密码未过期
                                    IsAccountOk,// 帐户未锁定
                                    AuthorityUtils.NO_AUTHORITIES,
                                    userName,
                                    companyCode,
                                    mailAddress,
                                    deptnm,
                                    status, pwdErrorCount, comId, deptId);
                            return MasterUserDetailsManager.this.masterAuthenticatedUser;
                        }

                    });
        } catch (final SystemError ex) {
            throw new RecoverableDataAccessException(ex.getMessage(), ex);
        }
    }

    /**
     */
    @Override
    protected List<GrantedAuthority> loadUserAuthorities(final String aUsername) {

        final SisqpFwPrincipal principal = new SisqpFwPrincipal(aUsername);
        final String loginCompanyCode = principal.getCompanyCode();
        String companyCode = null;
        if (StringUtils.isEqual(this.companyCodeForLogin, loginCompanyCode)) {
            companyCode = this.findDefaultCompanycd(principal.getUserId());
        } else {
            companyCode = loginCompanyCode;
        }

        final DataSource companyDataSource = this.findCompanyDataSource(companyCode);

        try {

            final List<GrantedAuthority> result = this.createJdbcTemplate(companyDataSource).query(
                    this.getAuthoritiesByUsernameQuery(),
                    new String[] {principal.getUserId()},
                    new RowMapper<GrantedAuthority>() {

                        @SuppressWarnings("synthetic-access")
                        public GrantedAuthority mapRow(final ResultSet aRs, final int aRowNum) throws SQLException {
                            final String roleName =
                                    MasterUserDetailsManager.this.getRolePrefix() + aRs.getString(C_COL_ROLE_CODE);
                            final GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
                            return authority;
                        }
                    });
            this.masterAuthenticatedUser.setAuthorities(result);
            return result;
        } catch (final SystemError ex) {
            throw new RecoverableDataAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    protected UserDetails createUserDetails(final String aUsername, final UserDetails aUserFromUserQuery,
            final List<GrantedAuthority> aCombinedAuthorities) {
        if (aUserFromUserQuery instanceof MasterAuthenticatedUser) {
            return this.masterAuthenticatedUser;
        }
        throw new ApplicationException(new SimpleAppMessage("XFW90004"));
    }

    /**
     */
    private SisqpFwPrincipal createPrincipal(final String aUsername) {
        final SisqpFwPrincipal principal = new SisqpFwPrincipal(aUsername);
        if (StringUtils.isNotEmpty(principal.getCompanyCode())) {
            return principal;
        }

        return new SisqpFwPrincipal(this.companyCodeForLogin, aUsername);
    }

    private String findDefaultCompanycd(final String aUsername) {
        return this.createJdbcTemplate(this.findLoginDataSource()).queryForList(
                this.defaultCompanyQuery,
                new String[] {aUsername}, String.class).get(0);

    }

    /**
     */
    private DataSource findLoginDataSource() {
        return this.findCompanyDataSource(this.companyCodeForLogin);
    }

    /**
     */
    private DataSource findCompanyDataSource(String aCompanycd) {
        if (StringUtils.isEmpty(aCompanycd)) {
            aCompanycd = "000";
        }
        final String dsName = this.dataSourcePrefix + aCompanycd;
        try {
            final ApplicationContext context = BeanDefineInfo.getApplicationContext();
            final DataSource dataSource = context.getBean(dsName, DataSource.class);
            return dataSource;
        } catch (final BeansException e) {
            e.printStackTrace();
            // throw new InvalidCompanycdException(new SimpleAppMessage("XFW70006").format(dsName).toString(), e);
            return null;
        }

    }

    private boolean checkServer() {
        final boolean res = false;
        // ConfigFileHolder.setFilePath("sys_config.properties");
        // final String macAddress = ConfigFileHolder.getString("macAddress");
        // final String localMac = SoftWareUtils.getMacByOS();
        // if (StringUtils.isNotEmpty(macAddress) && macAddress.equals(localMac)) {
        // res = true;
        // }

        return res;
    }

    /**
     * setter for dataSourcePrefix.
     * 
     * @param aDataSourcePrefix dataSourcePrefix
     */
    public void setDataSourcePrefix(final String aDataSourcePrefix) {
        this.dataSourcePrefix = aDataSourcePrefix;
    }

    /**
     * setter for companyCodeForLogin.
     * 
     * @param aCompanyCodeForLogin companyCodeForLogin
     */
    public void setCompanycdForLogin(final String aCompanyCodeForLogin) {
        this.companyCodeForLogin = aCompanyCodeForLogin;
    }

    /**
     * setter for defaultCompanyQuery.
     * 
     * @param aDefaultCompanyQuery defaultCompanyQuery
     */
    public void setDefaultCompanyQuery(final String aDefaultCompanyQuery) {
        this.defaultCompanyQuery = aDefaultCompanyQuery;
    }

}
