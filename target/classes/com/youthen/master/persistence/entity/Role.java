// ============================================================
// Copyright(c) Pro-Ship Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class Role extends AbstractCommonEntity {

    /**
     * 。
     */
    private static final long serialVersionUID = -8492994865668598568L;

    private Long id;
    private String code;
    private String name;

    private Long companyId;
    private Long systemId;
    private SubSystem systemName;
    private Short isEdit;

    private Long recorderTypeId;
    private Long usersCountsFlag;
    private Long titleLevel;
    private Long titleType;
    private String remark;
    private String recorderTypeCode;
    private Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合

    String createrId;
    Date createTime;

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

    /**
     * getter for usersCountsFlag.
     * 
     * @return usersCountsFlag
     */
    public Long getUsersCountsFlag() {
        return this.usersCountsFlag;
    }

    /**
     * setter for usersCountsFlag.
     * 
     * @param aUsersCountsFlag usersCountsFlag
     */
    public void setUsersCountsFlag(final Long aUsersCountsFlag) {
        this.usersCountsFlag = aUsersCountsFlag;
    }

    /**
     * getter for titleLevel.
     * 
     * @return titleLevel
     */
    public Long getTitleLevel() {
        return this.titleLevel;
    }

    /**
     * setter for titleLevel.
     * 
     * @param aTitleLevel titleLevel
     */
    public void setTitleLevel(final Long aTitleLevel) {
        this.titleLevel = aTitleLevel;
    }

    /**
     * getter for titleType.
     * 
     * @return titleType
     */
    public Long getTitleType() {
        return this.titleType;
    }

    /**
     * setter for titleType.
     * 
     * @param aTitleType titleType
     */
    public void setTitleType(final Long aTitleType) {
        this.titleType = aTitleType;
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
     * getter for recorderTypeCode.
     * 
     * @return recorderTypeCode
     */
    public String getRecorderTypeCode() {
        return this.recorderTypeCode;
    }

    /**
     * setter for recorderTypeCode.
     * 
     * @param aRecorderTypeCode recorderTypeCode
     */
    public void setRecorderTypeCode(final String aRecorderTypeCode) {
        this.recorderTypeCode = aRecorderTypeCode;
    }

    /**
     * getter for users.
     * 
     * @return users
     */
    public Set<LoginUser> getUsers() {
        return this.users;
    }

    /**
     * setter for users.
     * 
     * @param aUsers users
     */
    public void setUsers(final Set<LoginUser> aUsers) {
        this.users = aUsers;
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
     * getter for systemName.
     * 
     * @return systemName
     */
    public SubSystem getSystemName() {
        return this.systemName;
    }

    /**
     * setter for systemName.
     * 
     * @param aSystemName systemName
     */
    public void setSystemName(final SubSystem aSystemName) {
        this.systemName = aSystemName;
    }

    /**
     * getter for isEdit.
     * 
     * @return isEdit
     */
    public Short getIsEdit() {
        return this.isEdit;
    }

    /**
     * setter for isEdit.
     * 
     * @param aIsEdit isEdit
     */
    public void setIsEdit(final Short aIsEdit) {
        this.isEdit = aIsEdit;
    }

}
