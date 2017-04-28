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
public class DeptRole extends AbstractCommonEntity {

    /**
     *
     */
    private static final long serialVersionUID = -6742992960502206616L;

    private Long id;
    private Long departmentId;
    private Long roleId;
    private Long recorderTypeId;
    private String systemCode;
    private String userId;

    /**
     * getter for id.
     * 
     * @return id
     */
    @Override
    public Long getId() {
        return this.id;
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
     * getter for departmentId.
     * 
     * @return departmentId
     */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * setter for departmentId.
     * 
     * @param aDepartmentId departmentId
     */
    public void setDepartmentId(final Long aDepartmentId) {
        this.departmentId = aDepartmentId;
    }

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

    public String getSystemCode() {
        return this.systemCode;
    }

    public void setSystemCode(final String aSystemCode) {
        this.systemCode = aSystemCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String aUserId) {
        this.userId = aUserId;
    }

    public Long getRecorderTypeId() {
        return this.recorderTypeId;
    }

    public void setRecorderTypeId(final Long aRecorderTypeId) {
        this.recorderTypeId = aRecorderTypeId;
    }

}
