package com.youthen.master.persistence.entity;

import com.youthen.framework.common.ConfigFileHolder;
import com.youthen.framework.common.StringUtils;

public class LockNoticeEmail {

    private String userCode;
    private String userName;
    private String userDeptName;
    private String lockTime;

    public String getEmailContent(final LockNoticeEmail email) {
        ConfigFileHolder.setFilePath("webmessage.properties");
        String emailLockNotice = "";
        emailLockNotice = ConfigFileHolder.getString("email_lock_notice");

        if (StringUtils.isNotEmpty(emailLockNotice)) {
            emailLockNotice = emailLockNotice.replace("${userCode}", email.getUserCode());
            emailLockNotice = emailLockNotice.replace("${userName}", email.getUserName());
            emailLockNotice = emailLockNotice.replace("${userDeptName}", email.getUserDeptName());
            emailLockNotice = emailLockNotice.replace("${lockTime}", email.getLockTime());
        }

        return emailLockNotice;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(final String aUserCode) {
        this.userCode = aUserCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String aUserName) {
        this.userName = aUserName;
    }

    public String getUserDeptName() {
        return this.userDeptName;
    }

    public void setUserDeptName(final String aUserDeptName) {
        this.userDeptName = aUserDeptName;
    }

    public String getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(final String aLockTime) {
        this.lockTime = aLockTime;
    }

}
