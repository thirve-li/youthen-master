// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.youthen.framework.common.StringUtils;
import com.youthen.master.persistence.entity.Article;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Kbn;

/**
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class KbnDto extends MasterEntryDto {

    private static final long serialVersionUID = 8934042717415936832L;
    private Long id;
    private String type;
    private String typeName;
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

    private String bigTypeId;

    private Company company;

    String notForce;// 建议校准周期（三九器具二级分类用）
    String force;// 强制校准周期（三九器具二级分类用）

    private List<Kbn> childKbnList = new ArrayList<Kbn>();

    private List<Article> articleKbnList = new ArrayList<Article>();

    private Long bigColumnId;

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
        super.setObjectName(aNameCn);
        this.nameCn = aNameCn;
    }

    /**
     * getter for nameEn.
     * 公司为三九时，此字段为强制校准周期和建议校准周期，逗号分隔"强制校准周期,建议校准周期"
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
     * getter for typeName.
     * 
     * @return typeName
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * setter for typeName.
     * 
     * @param aTypeName typeName
     */
    public void setTypeName(final String aTypeName) {
        this.typeName = aTypeName;
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
     * getter for bigTypeId.
     * 
     * @return bigTypeId
     */
    public String getBigTypeId() {
        return this.bigTypeId;
    }

    /**
     * setter for bigTypeId.
     * 
     * @param aBigTypeId bigTypeId
     */
    public void setBigTypeId(final String aBigTypeId) {
        this.bigTypeId = aBigTypeId;
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
     * getter for notForce.
     * 
     * @return notForce
     */
    public String getNotForce() {
        if (StringUtils.isNotEmpty(this.nameEn)) {
            final String[] ary = this.nameEn.split(",");
            if (ary != null && ary.length > 1) {

                if (ary[1] == null) {
                    ary[1] = "";
                }

                this.notForce = ary[1];
            }
        }

        return this.notForce;
    }

    /**
     * setter for notForce.
     * 
     * @param aNotForce notForce
     */
    public void setNotForce(final String aNotForce) {
        this.notForce = aNotForce;
    }

    /**
     * getter for force.
     * 
     * @return force
     */
    public String getForce() {
        if (StringUtils.isNotEmpty(this.nameEn)) {
            final String[] ary = this.nameEn.split(",");
            if (ary != null && ary.length > 1) {

                if (ary[0] == null) {
                    ary[0] = "";
                }

                this.force = ary[0];
            }
        }
        return this.force;
    }

    /**
     * setter for force.
     * 
     * @param aForce force
     */
    public void setForce(final String aForce) {
        this.force = aForce;
    }

    /**
     * getter for childKbnList.
     * 
     * @return childKbnList
     */
    public List<Kbn> getChildKbnList() {
        return this.childKbnList;
    }

    /**
     * setter for childKbnList.
     */
    public void setChildKbnList(final List<Kbn> childKbnList) {
        this.childKbnList = childKbnList;
    }

    /**
     * getter for articleKbnList.
     * 
     * @return articleKbnList
     */
    public List<Article> getArticleKbnList() {
        return this.articleKbnList;
    }

    /**
     * setter for articleKbnList.
     * 
     * @param aArticleKbnList articleKbnList
     */
    public void setArticleKbnList(final List<Article> aArticleKbnList) {
        this.articleKbnList = aArticleKbnList;
    }

    /**
     * getter for bigColumnId.
     * 
     * @return bigColumnId
     */
    public Long getBigColumnId() {
        return this.bigColumnId;
    }

    /**
     * setter for bigColumnId.
     * 
     * @param aBigColumnId bigColumnId
     */
    public void setBigColumnId(final Long aBigColumnId) {
        this.bigColumnId = aBigColumnId;
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
