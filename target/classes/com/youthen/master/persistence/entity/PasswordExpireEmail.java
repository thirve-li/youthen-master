// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import com.youthen.framework.common.ConfigFileHolder;
import com.youthen.framework.common.StringUtils;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class PasswordExpireEmail {

    private String userCode;
    private String userName;
    private String expireTime;

    public String getEmailContent(final PasswordExpireEmail email) {
        ConfigFileHolder.setFilePath("webmessage.properties");
        String passwordExpireNotice = "";
        passwordExpireNotice = ConfigFileHolder.getString("password_expire");

        if (StringUtils.isNotEmpty(passwordExpireNotice)) {
            passwordExpireNotice = passwordExpireNotice.replace("${userCode}", email.getUserCode());
            passwordExpireNotice = passwordExpireNotice.replace("${userName}", email.getUserName());
            passwordExpireNotice = passwordExpireNotice.replace("${expireTime}", email.getExpireTime());
        }

        return passwordExpireNotice;
    }

    /**
     * getter for userCode.
     * 
     * @return userCode
     */
    public String getUserCode() {
        return this.userCode;
    }

    /**
     * setter for userCode.
     * 
     * @param aUserCode userCode
     */
    public void setUserCode(final String aUserCode) {
        this.userCode = aUserCode;
    }

    /**
     * getter for userName.
     * 
     * @return userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * setter for userName.
     * 
     * @param aUserName userName
     */
    public void setUserName(final String aUserName) {
        this.userName = aUserName;
    }

    /**
     * getter for expireTime.
     * 
     * @return expireTime
     */
    public String getExpireTime() {
        return this.expireTime;
    }

    /**
     * setter for expireTime.
     * 
     * @param aExpireTime expireTime
     */
    public void setExpireTime(final String aExpireTime) {
        this.expireTime = aExpireTime;
    }

}
