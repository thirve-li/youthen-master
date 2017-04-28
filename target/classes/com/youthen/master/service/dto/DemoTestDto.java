// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;


/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class DemoTestDto extends MasterEntryDto {

    private static final long serialVersionUID = -1862982254463161989L;

    private String email;
    private int age;
    private Long id;

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

    private String name;

    /**
     * @see com.youthen.framework.service.dto.BaseDto#getId()
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

}
