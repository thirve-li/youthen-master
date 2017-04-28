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
public class CompanyWorkflowDto extends MasterEntryDto {

    /**
     */
    private static final long serialVersionUID = 6132640591353472502L;

    private Long id;
    private Long companyId;
    private Long recorderTypeId;

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
     * getter for recorderTypeId.
     * 
     * @return recorderTypeId
     */
    public Long getRecorderTypeId() {
        return this.recorderTypeId;
    }

    /**
     * setter for recorderTypeId.
     * 
     * @param aRecorderTypeId recorderTypeId
     */
    public void setRecorderTypeId(final Long aRecorderTypeId) {
        this.recorderTypeId = aRecorderTypeId;
    }

}
