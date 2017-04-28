// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class DemoTest extends AbstractCommonEntity {

    /**
     * @see com.youthen.framework.persistence.entity.CommonEntity#getId()
     */

    private static final long serialVersionUID = -1862982254463161989L;

    private Long id;
    private String name;
    private int age;
    private String email;

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
     * getter for user_name.
     * 
     * @return user_name
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for user_name.
     * 
     * @param aUser_name user_name
     */
    public void setName(final String aUser_name) {
        this.name = aUser_name;
    }

    /**
     * getter for age.
     * 
     * @return age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * setter for age.
     * 
     * @param aAge age
     */
    public void setAge(final int aAge) {
        this.age = aAge;
    }

    /**
     * getter for email.
     * 
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * setter for email.
     * 
     * @param aEmail email
     */
    public void setEmail(final String aEmail) {
        this.email = aEmail;
    }

}
