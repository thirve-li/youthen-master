// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import java.io.File;
import java.sql.Blob;
import java.util.HashSet;
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
public class CompanyDto extends MasterEntryDto {

    /**
     * 。
     */
    private static final long serialVersionUID = 3406671050897684514L;

    private Long id;
    private Long parentCompanyId;
    private String code;
    private String name;
    private String contacter;
    private String phone;
    private String fax;
    private String address;
    private String zipCode;
    private Long status;
    private File headerFile;

    private File footerFile;

    private Blob headerLogo;
    private Blob footerLogo;
    private Set<LoginUser> users = new HashSet<LoginUser>();// 用户集合

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

    private String fileName; // 大文本的fileName
    private String fileType; // 大文本的fileType

    /**
     * getter for fileName.
     * 
     * @return fileName
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * setter for fileName.
     * 
     * @param aFileName fileName
     */
    public void setFileName(final String aFileName) {
        this.fileName = aFileName;
    }

    /**
     * getter for fileType.
     * 
     * @return fileType
     */
    public String getFileType() {
        return this.fileType;
    }

    /**
     * setter for fileType.
     * 
     * @param aFileType fileType
     */
    public void setFileType(final String aFileType) {
        this.fileType = aFileType;
    }

    @Override
    public String getObjectName() {
        return this.name;
    }

    public File getHeaderFile() {
        return this.headerFile;
    }

    public void setHeaderFile(final File aHeaderFile) {
        this.headerFile = aHeaderFile;
    }

    public File getFooterFile() {
        return this.footerFile;
    }

    public void setFooterFile(final File aFooterFile) {
        this.footerFile = aFooterFile;
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
}
