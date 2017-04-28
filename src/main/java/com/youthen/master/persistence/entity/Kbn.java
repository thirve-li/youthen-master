// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class Kbn extends AbstractCommonEntity {

    private Long id;
    private String type;
    private String code;
    private String nameCn;
    private String nameEn;
    private Long parentTypeId;
    private String image;
    private Long status;
    private Long isEdit;
    private String remark;

    private String createrId;
    private Long companyId;
    private Date createTime;

    private Kbn parentType;

    private Company company;

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
     * getter for type.
     * 
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * setter for type.
     * 
     * @param aType type
     */
    public void setType(final String aType) {
        this.type = aType;
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
     * getter for nameCn.
     * 
     * @return nameCn
     */
    public String getNameCn() {
        return this.nameCn;
    }

    /**
     * setter for nameCn.
     * 
     * @param aNameCn nameCn
     */
    public void setNameCn(final String aNameCn) {
        this.nameCn = aNameCn;
    }

    /**
     * getter for nameEn.
     * 
     * @return nameEn
     */
    public String getNameEn() {
        return this.nameEn;
    }

    /**
     * setter for nameEn.
     * 
     * @param aNameEn nameEn
     */
    public void setNameEn(final String aNameEn) {
        this.nameEn = aNameEn;
    }

    /**
     * getter for parentTypeId.
     * 
     * @return parentTypeId
     */
    public Long getParentTypeId() {
        if (this.parentType != null) {
            return this.parentType.getId();
        }
        return this.parentTypeId;
    }

    /**
     * setter for parentTypeId.
     * 
     * @param aParentTypeId parentTypeId
     */
    public void setParentTypeId(final Long aParentTypeId) {
        this.parentTypeId = aParentTypeId;
    }

    /**
     * getter for status.
     * 
     * @return status
     */
    public Long getStatus() {
        return this.status;
    }

    /**
     * setter for status.
     * 
     * @param aStatus status
     */
    public void setStatus(final Long aStatus) {
        this.status = aStatus;
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
     * getter for remark.
     * 
     * @return remark
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * setter for remark.
     * 
     * @param aRemark remark
     */
    public void setRemark(final String aRemark) {
        this.remark = aRemark;
    }

    /**
     * getter for createrId.
     * 
     * @return createrId
     */
    public String getCreaterId() {
        return this.createrId;
    }

    /**
     * setter for createrId.
     * 
     * @param aCreaterId createrId
     */
    public void setCreaterId(final String aCreaterId) {
        this.createrId = aCreaterId;
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
     * getter for createTime.
     * 
     * @return createTime
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * setter for createTime.
     * 
     * @param aCreateTime createTime
     */
    public void setCreateTime(final Date aCreateTime) {
        this.createTime = aCreateTime;
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

    @Override
    public String getObjectName() {
        return this.nameCn;
    }

    /**
     * getter for company.
     * 
     * @return company
     */
    public Company getCompany() {
        return this.company;
    }

    /**
     * setter for company.
     * 
     * @param aCompany company
     */
    public void setCompany(final Company aCompany) {
        this.company = aCompany;
    }

    /**
     * getter for image.
     * 
     * @return image
     */
    public String getImage() {
        return this.image;
    }

    /**
     * setter for image.
     * 
     * @param aImage image
     */
    public void setImage(final String aImage) {
        this.image = aImage;
    }
}
