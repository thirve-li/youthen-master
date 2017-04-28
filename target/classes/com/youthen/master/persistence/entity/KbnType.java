// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class KbnType extends AbstractCommonEntity {

    /**
     * 。
     */
    private static final long serialVersionUID = 4991857994399122652L;
    private Long id;
    private String name;
    private String typeCode;
    private Long isEdit;
    private Long parentId;

    private KbnType parentType;

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
    public KbnType getParentType() {
        return this.parentType;
    }

    /**
     * setter for parentType.
     * 
     * @param aParentType parentType
     */
    public void setParentType(final KbnType aParentType) {
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
