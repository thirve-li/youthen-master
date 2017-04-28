// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class UserRole extends AbstractCommonEntity {

    /**
     *
     */
    private static final long serialVersionUID = -6742992960502206616L;

    private Long id;
    private Long roleId;
    private String userId;
    private Long companyId;
    private Long deptId;
    private Long sysId;

    /**
     * getter for roleId.
     * 
     * @return roleId
     */
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * setter for roleId.
     * 
     * @param aRoleId roleId
     */
    public void setRoleId(final Long aRoleId) {
        this.roleId = aRoleId;
    }

    /**
     * getter for userId.
     * 
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * setter for userId.
     * 
     * @param aUserId userId
     */
    public void setUserId(final String aUserId) {
        this.userId = aUserId;
    }

    /**
     * getter for companyId.
     * 
     * @return companyId
     */
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * setter for companyId.
     * 
     * @param aCompanyId companyId
     */
    public void setCompanyId(final Long aCompanyId) {
        this.companyId = aCompanyId;
    }

    /**
     * getter for deptId.
     * 
     * @return deptId
     */
    public Long getDeptId() {
        return this.deptId;
    }

    /**
     * setter for deptId.
     * 
     * @param aDeptId deptId
     */
    public void setDeptId(final Long aDeptId) {
        this.deptId = aDeptId;
    }

    /**
     * getter for sysId.
     * 
     * @return sysId
     */
    public Long getSysId() {
        return this.sysId;
    }

    /**
     * setter for sysId.
     * 
     * @param aSysId sysId
     */
    public void setSysId(final Long aSysId) {
        this.sysId = aSysId;
    }

    /**
     * setter for id.
     * 
     * @param aId id
     */
    public void setId(final Long aId) {
        this.id = aId;
    }

    /**
     * getter for sysId.
     * 
     * @return sysId
     */
    @Override
    public Long getId() {
        return this.id;
    }

}
