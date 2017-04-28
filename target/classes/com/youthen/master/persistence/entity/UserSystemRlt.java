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
public class UserSystemRlt extends AbstractCommonEntity {

    /**
     * 。
     */
    private static final long serialVersionUID = -4229110470700815511L;
    private Long id;
    private Long systemId;
    private String userId;

    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * getter for systemId.
     * 
     * @return systemId
     */
    public Long getSystemId() {
        return this.systemId;
    }

    /**
     * setter for systemId.
     * 
     * @param aSystemId systemId
     */
    public void setSystemId(final Long aSystemId) {
        this.systemId = aSystemId;
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
