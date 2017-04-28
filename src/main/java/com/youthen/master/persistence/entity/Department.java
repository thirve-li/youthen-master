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
public class Department extends AbstractCommonEntity {

    /**
     */
    private static final long serialVersionUID = 429190005483438141L;

    private Long id;
    private Long companyId;
    private Long parentDepartmentId;
    private String name;
    private String code;
    private Long rootId;
    private Long status;

    private String createrId;
    private Date createTime;
    private Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合

    // 部门下所有人
    // private Set<LoginUser> users = new HashSet<LoginUser>();

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
     * @param aDepartmentId id
     */
    public void setId(final Long aDepartmentId) {
        this.id = aDepartmentId;
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
     * @param aDepartmentName name
     */
    public void setName(final String aDepartmentName) {
        this.name = aDepartmentName;
    }

    public Long getRootId() {
        return this.rootId;
    }

    public void setRootId(final Long aRootId) {
        this.rootId = aRootId;
    }

    /**
     * getter for users.
     * 
     * @return users
     */
    // public Set<LoginUser> getUsers() {
    // return this.users;
    // }
    //
    // public void setUsers(final Set<LoginUser> aUsers) {
    // this.users = aUsers;
    // }

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

    // private final Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合
    // private final Role role = new Role();// 角色集合
    // private final Set<Equipment> equipments = new HashSet<Equipment>();// 设备集合

}
