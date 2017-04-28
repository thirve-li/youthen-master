// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import com.youthen.framework.common.annotation.Dto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Dto
public class RoleFunctionDto extends MasterEntryDto {

    private static final long serialVersionUID = 6215759114790104564L;

    private Long id;
    private Long roleId;
    private Long functionId;

    /**
     * getter for id.
     * 
     * @return id
     */
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
     * getter for functionId.
     * 
     * @return functionId
     */
    public Long getFunctionId() {
        return this.functionId;
    }

    /**
     * setter for functionId.
     * 
     * @param aFunctionId functionId
     */
    public void setFunctionId(final Long aFunctionId) {
        this.functionId = aFunctionId;
    }

}
