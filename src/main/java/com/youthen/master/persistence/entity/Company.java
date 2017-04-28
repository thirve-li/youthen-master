// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class Company extends AbstractCommonEntity {

    /**
     */
    private static final long serialVersionUID = -1862982254463161989L;

    private Long id;
    private Long parentCompanyId;
    private String code;
    private String name;
    private String contacter;
    private String phone;
    private String fax;
    private String address;
    private String zipCode;

    private Blob headerLogo;
    private Blob footerLogo;

    private Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合

    String createrId;
    Date createTime;

    Long status;

    // private Set<SubSystem> subSystems = new HashSet<SubSystem>();

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
     * @param aCompanyId id
     */
    public void setId(final Long aCompanyId) {
        this.id = aCompanyId;
    }

    /**
     * getter for parentCompanyId.
     * 
     * @return parentCompanyId
     */
    public Long getParentCompanyId() {
        return this.parentCompanyId;
    }

    /**
     * setter for parentCompanyId.
     * 
     * @param aParentCompanyId parentCompanyId
     */
    public void setParentCompanyId(final Long aParentCompanyId) {
        this.parentCompanyId = aParentCompanyId;
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
     * @param aCompanyCode code
     */
    public void setCode(final String aCompanyCode) {
        this.code = aCompanyCode;
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
     * @param aCompanyName name
     */
    public void setName(final String aCompanyName) {
        this.name = aCompanyName;
    }

    /**
     * getter for contacter.
     * 
     * @return contacter
     */
    public String getContacter() {
        return this.contacter;
    }

    /**
     * setter for contacter.
     * 
     * @param aContacter contacter
     */
    public void setContacter(final String aContacter) {
        this.contacter = aContacter;
    }

    /**
     * getter for phone.
     * 
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * setter for phone.
     * 
     * @param aPhone phone
     */
    public void setPhone(final String aPhone) {
        this.phone = aPhone;
    }

    /**
     * getter for fax.
     * 
     * @return fax
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * setter for fax.
     * 
     * @param aFax fax
     */
    public void setFax(final String aFax) {
        this.fax = aFax;
    }

    /**
     * getter for address.
     * 
     * @return address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * setter for address.
     * 
     * @param aAddress address
     */
    public void setAddress(final String aAddress) {
        this.address = aAddress;
    }

    /**
     * getter for zipCode.
     * 
     * @return zipCode
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * setter for zipCode.
     * 
     * @param aZipCode zipCode
     */
    public void setZipCode(final String aZipCode) {
        this.zipCode = aZipCode;
    }

    /**
     * getter for headerLogo.
     * 
     * @return headerLogo
     */
    public Blob getHeaderLogo() {
        return this.headerLogo;
    }

    /**
     * setter for headerLogo.
     * 
     * @param aHeaderLogo headerLogo
     */
    public void setHeaderLogo(final Blob aHeaderLogo) {
        this.headerLogo = aHeaderLogo;
    }

    /**
     * getter for footerLogo.
     * 
     * @return footerLogo
     */
    public Blob getFooterLogo() {
        return this.footerLogo;
    }

    /**
     * setter for footerLogo.
     * 
     * @param aFooterLogo footerLogo
     */
    public void setFooterLogo(final Blob aFooterLogo) {
        this.footerLogo = aFooterLogo;
    }

    // public void setSubSystems(final Set<SubSystem> SubSystems) {
    // this.subSystems = SubSystems;
    // }
    //
    // public Set<SubSystem> getSubSystems() {
    // return this.subSystems;
    // }

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

    /**
     * @see com.youthen.framework.persistence.entity.CommonEntity#setId(java.io.Serializable)
     */

}
