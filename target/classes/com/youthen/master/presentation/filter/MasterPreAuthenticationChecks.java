package com.youthen.master.presentation.filter;

import javax.annotation.Resource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.common.security.AuthenticatedUser;
import com.youthen.framework.common.security.CredentialsLockedException;
import com.youthen.master.service.SystemConfigService;
import com.youthen.master.service.dto.SystemConfigDto;

public class MasterPreAuthenticationChecks implements UserDetailsChecker {

    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Resource
    SystemConfigService systemConfigService;

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsChecker#check(org.springframework.security.core.userdetails.UserDetails)
     */
    @Override
    public void check(final UserDetails aToCheck) {

        if (!aToCheck.isAccountNonLocked()) {
            throw new LockedException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked",
                        "User account is locked"), aToCheck);
        }

        if (!aToCheck.isEnabled()) {
            throw new DisabledException(this.messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.disabled",
                        "User is disabled"), aToCheck);
        }

        if (!aToCheck.isAccountNonExpired()) {
            throw new AccountExpiredException(this.messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.expired",
                        "User account has expired"), aToCheck);
        }

        if (aToCheck instanceof AuthenticatedUser) {// 密码输入错误次数超限制
            final AuthenticatedUser user = (AuthenticatedUser) aToCheck;

            try {
                final SystemConfigDto sysConfigDto = this.systemConfigService.load();
                if (user.getPasswordErrorCount() >= sysConfigDto.getPwdErrTime()) {
                    throw new CredentialsLockedException(new FieldSupportedMessage("XFW73001").toString(), user);
                }
            } catch (final ObjectNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
