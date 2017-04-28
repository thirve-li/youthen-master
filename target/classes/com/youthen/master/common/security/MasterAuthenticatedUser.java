package com.youthen.master.common.security;

import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import com.youthen.framework.common.security.AuthenticatedUser;
import com.youthen.framework.common.security.SisqpFwPrincipal;

public class MasterAuthenticatedUser extends AuthenticatedUser {

    public static final String ROLE_SYSTEM = "SystemAdministrator";

    public static final String ROLE_USER = "LoginOnly";

    public static final String SYSTEM_COMPANY_CODE = "000";
    private static final long serialVersionUID = 351855033399496731L;

    private final String companyCode;

    private final String usernm;

    private final String deptnm;

    private final String mailAddress;

    private final String status;

    Collection<GrantedAuthority> authorities;

    /**
     * getter for authorities.
     * 
     * @return authorities
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * setter for authorities.
     * 
     * @param aAuthorities authorities
     */
    public void setAuthorities(final Collection<GrantedAuthority> aAuthorities) {
        this.authorities = aAuthorities;
    }

    public MasterAuthenticatedUser(
            final SisqpFwPrincipal aUser,
            final String aPassword,
            final boolean aEnabled,// 用户激活
            final boolean aAccountNonExpired,// 帐户未过期
            final boolean aCredentialsNonExpired,// 证书\密码未过期
            final boolean aAccountNonLocked,// 帐户未锁定
            final Collection<GrantedAuthority> aAuthorities,
            final String aUserName,
            final String aCompanyCode,
            final String aMailAddress,
            final String deptnm,
            final String aStatus, final int passwordErrorCount, final Long companyId, final Long departmentId) {

        super(aUser, aPassword, aEnabled, aAccountNonExpired, aCredentialsNonExpired, aAccountNonLocked, aAuthorities,
                passwordErrorCount);
        super.setCompanyId(companyId);
        super.setDepartmentId(departmentId);
        super.getUser().setCompanyCode(aCompanyCode);
        this.companyCode = aCompanyCode;
        this.usernm = aUserName;
        this.deptnm = deptnm;
        this.mailAddress = aMailAddress;
        this.status = aStatus;

    }

    /**
     * getter for companyCode.
     * 
     * @return companyCode
     */
    public String getCompanyName() {
        return this.companyCode;
    }

    /**
     * getter for usernm.
     * 
     * @return usernm
     */
    public String getUsernm() {
        return this.usernm;
    }

    /**
     * getter for mailAddress.
     * 
     * @return mailAddress
     */
    public String getMailAddress() {
        return this.mailAddress;
    }

    public boolean hasSystemRole() {
        if (this.getAuthorities() == null) {
            return false;
        }
        final Iterator<GrantedAuthority> it = (this.getAuthorities()).iterator();
        while (it.hasNext()) {
            final GrantedAuthority auth = it.next();
            if (ROLE_SYSTEM.equals(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null & aObject instanceof MasterAuthenticatedUser) {
            final MasterAuthenticatedUser anotherInstance = (MasterAuthenticatedUser) aObject;

            final EqualsBuilder builder = new EqualsBuilder();
            builder.append(this.getUserId(), anotherInstance.getUserId());
            return builder.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getUserId()).hashCode();
    }

    /**
     * getter for status.
     * 
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * getter for deptnm.
     * 
     * @return deptnm
     */
    public String getDeptnm() {
        return this.deptnm;
    }

    public static class Builder {

        /** */
        private SisqpFwPrincipal user;
        /** */
        private String password;
        /** */
        private boolean enabled;
        /** */
        private boolean accountNonExpired;
        /** */
        private boolean credentialsNonExpired;
        /** */
        private boolean accountNonLocked;
        /** */
        private Collection<GrantedAuthority> authorities;

        /** */
        private String userName;

        /** */
        private String companyCode;
        /** */
        private String mailAddress;
        private String deptnm;
        private int pwdErrorCount;

        private Long companyId;

        private Long departmentId;

        /** */
        private String status;

        public MasterAuthenticatedUser build() {
            return new MasterAuthenticatedUser(this.user, this.password, this.enabled, this.accountNonExpired,
                    this.credentialsNonExpired, this.accountNonLocked, this.authorities,
                    this.userName, this.companyCode, this.mailAddress, this.deptnm, this.status, this.pwdErrorCount,
                    this.companyId, this.departmentId);
        }

        public Builder setUser(final SisqpFwPrincipal aUser) {
            this.user = aUser;
            return this;
        }

        public Builder setPassword(final String aPassword) {
            this.password = aPassword;
            return this;
        }

        public Builder setPwdErrorCount(final int pwdErrorCount) {
            this.pwdErrorCount = pwdErrorCount;
            return this;
        }

        public Builder setEnabled(final boolean aEnabled) {
            this.enabled = aEnabled;
            return this;
        }

        public Builder setAccountNonExpired(final boolean aAccountNonExpired) {
            this.accountNonExpired = aAccountNonExpired;
            return this;
        }

        public Builder setCredentialsNonExpired(final boolean aCredentialsNonExpired) {
            this.credentialsNonExpired = aCredentialsNonExpired;
            return this;
        }

        public Builder setAccountNonLocked(final boolean aAccountNonLocked) {
            this.accountNonLocked = aAccountNonLocked;
            return this;
        }

        public Builder setAuthorities(final Collection<GrantedAuthority> aAuthorities) {
            this.authorities = aAuthorities;
            return this;
        }

        public Builder setUserName(final String aUserName) {
            this.userName = aUserName;
            return this;
        }

        public Builder setCompanyName(final String aCompanyCode) {
            this.companyCode = aCompanyCode;
            return this;
        }

        public Builder setMailAddress(final String aMailAddress) {
            this.mailAddress = aMailAddress;
            return this;
        }

        public Builder setDeptnm(final String deptnm) {
            this.deptnm = deptnm;
            return this;
        }

        public Builder setStatus(final String aStatus) {
            this.status = aStatus;
            return this;
        }

        public Builder setaAuthorities(final Collection<GrantedAuthority> aAuthorities) {
            this.authorities = aAuthorities;
            return this;
        }

    }

}
