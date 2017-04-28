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
public class FunctionDto extends MasterEntryDto {

    /**
     * 。
     */
    private static final long serialVersionUID = -7903605211175577394L;
    private Long id;
    private String code;
    private String name;
    private Long subSystemId;
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
     * getter for code.
     * 
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * setter for code.
     * 
     * @param aCode code
     */
    public void setCode(final String aCode) {
        this.code = aCode;
    }

    /**
     * getter for name.
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for name.
     * 
     * @param aName name
     */
    public void setName(final String aName) {
        this.name = aName;
    }

    /**
     * getter for subSystemId.
     * 
     * @return subSystemId
     */
    public Long getSubSystemId() {
        return this.subSystemId;
    }

    /**
     * setter for subSystemId.
     * 
     * @param aSubSystemId subSystemId
     */
    public void setSubSystemId(final Long aSubSystemId) {
        this.subSystemId = aSubSystemId;
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
