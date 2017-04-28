package com.youthen.master.service.dto;

import java.util.List;
import java.util.Set;
import com.youthen.framework.common.annotation.Dto;
import com.youthen.master.persistence.entity.LoginUser;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Dto
public class DepartmentDto extends MasterEntryDto {

    /**
     * 
     */
    private static final long serialVersionUID = -9077484361272089029L;

    private Long id;
    private Long companyId;
    private Long parentDepartmentId;
    private Long rootId;
    private String name;
    private String code;
    private String parentDepartmentName;
    private String companyName;
    private Long status;
    private List<DepartmentDto> childrenDto;

    private Set<LoginUser> users;

    // 功能id集合
    private Long[] roleIds;

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
     * getter for parentDepartmentId.
     * 
     * @return parentDepartmentId
     */
    public Long getParentDepartmentId() {
        return this.parentDepartmentId;
    }

    /**
     * setter for parentDepartmentId.
     * 
     * @param aParentDepartmentId parentDepartmentId
     */
    public void setParentDepartmentId(final Long aParentDepartmentId) {
        this.parentDepartmentId = aParentDepartmentId;
    }

    /**
     * getter for rootId.
     * 
     * @return rootId
     */
    public Long getRootId() {
        return this.rootId;
    }

    /**
     * setter for rootId.
     * 
     * @param aRootId rootId
     */
    public void setRootId(final Long aRootId) {
        this.rootId = aRootId;
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
     * getter for parentDepartmentName.
     * 
     * @return parentDepartmentName
     */
    public String getParentDepartmentName() {
        return this.parentDepartmentName;
    }

    /**
     * setter for parentDepartmentName.
     * 
     * @param aParentDepartmentName parentDepartmentName
     */
    public void setParentDepartmentName(final String aParentDepartmentName) {
        this.parentDepartmentName = aParentDepartmentName;
    }

    /**
     * getter for companyName.
     * 
     * @return companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * setter for companyName.
     * 
     * @param aCompanyName companyName
     */
    public void setCompanyName(final String aCompanyName) {
        this.companyName = aCompanyName;
    }

    /**
     * getter for childrenDto.
     * 
     * @return childrenDto
     */
    public List<DepartmentDto> getChildrenDto() {
        return this.childrenDto;
    }

    /**
     * setter for childrenDto.
     * 
     * @param aChildrenDto childrenDto
     */
    public void setChildrenDto(final List<DepartmentDto> aChildrenDto) {
        this.childrenDto = aChildrenDto;
    }

    /**
     * getter for roleIds.
     * 
     * @return roleIds
     */
    public Long[] getRoleIds() {
        return this.roleIds;
    }

    /**
     * setter for roleIds.
     * 
     * @param aRoleIds roleIds
     */
    public void setRoleIds(final Long[] aRoleIds) {
        this.roleIds = aRoleIds;
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

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(final Long aStatus) {
        this.status = aStatus;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String aCode) {
        this.code = aCode;
    }

    @Override
    public String getObjectName() {
        return this.getName();
    }
}
