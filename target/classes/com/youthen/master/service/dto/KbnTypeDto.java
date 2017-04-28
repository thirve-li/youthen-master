// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import com.youthen.master.persistence.entity.Kbn;

/**
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class KbnTypeDto extends MasterEntryDto {

    private static final long serialVersionUID = 8934042717415936832L;
    private Long id;
    private String name;
    private String typeCode;
    private Long isEdit;
    private Long parentId;

    private Kbn parentType;

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
     * getter for typeCode.
     * 
     * @return typeCode
     */
    public String getTypeCode() {
        return this.typeCode;
    }

    /**
     * setter for typeCode.
     * 
     * @param aTypeCode typeCode
     */
    public void setTypeCode(final String aTypeCode) {
        this.typeCode = aTypeCode;
    }

    /**
     * getter for isEdit.
     * 
     * @return isEdit
     */
    public Long getIsEdit() {
        return this.isEdit;
    }

    /**
     * setter for isEdit.
     * 
     * @param aIsEdit isEdit
     */
    public void setIsEdit(final Long aIsEdit) {
        this.isEdit = aIsEdit;
    }

    /**
     * getter for parentType.
     * 
     * @return parentType
     */
    public Kbn getParentType() {
        return this.parentType;
    }

    /**
     * setter for parentType.
     * 
     * @param aParentType parentType
     */
    public void setParentType(final Kbn aParentType) {
        this.parentType = aParentType;
    }

    /**
     * getter for parentId.
     * 
     * @return parentId
     */
    public Long getParentId() {
        return this.parentId;
    }

    /**
     * setter for parentId.
     * 
     * @param aParentId parentId
     */
    public void setParentId(final Long aParentId) {
        this.parentId = aParentId;
    }

}
