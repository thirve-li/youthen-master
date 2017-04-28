package com.youthen.master.persistence.entity;

import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class PwdHistory extends AbstractCommonEntity {

    private static final long serialVersionUID = 1L;

    private Long id;
    // private LoginUser user;
    private String userId;
    private String password;
    private Date createDate;

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
     * getter for userId.
     * 
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * setter for userId.
     * 
     * @param aUserId userId
     */
    public void setUserId(final String aUserId) {
        this.userId = aUserId;
    }

    /**
     * getter for password.
     * 
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * setter for password.
     * 
     * @param aPassword password
     */
    public void setPassword(final String aPassword) {
        this.password = aPassword;
    }

    /**
     * getter for createDate.
     * 
     * @return createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * setter for createDate.
     * 
     * @param aCreateDate createDate
     */
    public void setCreateDate(final Date aCreateDate) {
        this.createDate = aCreateDate;
    }

}
