// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author Administrator
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class UserDeptRlt extends AbstractCommonEntity {

    /**
     * @see com.youthen.framework.persistence.entity.CommonEntity#getId()
     */

    /**
    *
    */
    private static final long serialVersionUID = -6742992960502206616L;

    private Long id;
    private Long deptId;
    private String userId;

    @Override
    public Long getId() {
        return this.id;
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
     * setter for id.
     * 
     * @param aId id
     */
    public void setId(final Long aId) {
        this.id = aId;
    }

}
