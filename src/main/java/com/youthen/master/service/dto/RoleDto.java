// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import java.util.HashSet;
import java.util.Set;
import com.youthen.framework.common.annotation.Dto;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.SubSystem;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Dto
public class RoleDto extends MasterEntryDto {

    private static final long serialVersionUID = 1270752286243346382L;

    private Long id;
    private String name;
    private String code;
    private Long companyId;
    private Long departmentId;
    private Long recorderTypeId;
    private Long systemId;
    private SubSystem systemName;
    private Short isEdit;

    private Long usersCountsFlag;
    private Long titleLevel;
    private Long titleType;
    private String remark;
    private String recorderTypeCode;
    private Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合

    // 功能id集合
    private Long[] functionIds;

    /**
     * getter for companyId.
     * 
     * @return companyId
     */
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * getter for departmentId.
     * 
     * @return departmentId
     */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * setter for departmentId.
     * 
     * @param aDepartmentId departmentId
     */
    public void setDepartmentId(final Long aDepartmentId) {
        this.departmentId = aDepartmentId;
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
     * getter for functionIds.
     * 
     * @return functionIds
     */
    public Long[] getFunctionIds() {
        return this.functionIds;
    }

    /**
     * setter for functionIds.
     * 
     * @param aFunctionIds functionIds
     */
    public void setFunctionIds(final Long[] aFunctionIds) {
        this.functionIds = aFunctionIds;
    }

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

    public Set<LoginUser> getUsers() {
        return this.users;
    }

    public void setUsers(final Set<LoginUser> aUsers) {
        this.users = aUsers;
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
