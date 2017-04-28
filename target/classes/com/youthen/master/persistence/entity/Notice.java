// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class Notice extends AbstractCommonEntity {

    /**
     * 。
     */
    private static final long serialVersionUID = -1393066067391786371L;

    private Long id;

    private String noticeTitle;

    private String noticeContent;

    private LoginUser creater;

    private Date createDate;

    private Integer status;

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
     * getter for noticeTitle.
     * 
     * @return noticeTitle
     */
    public String getNoticeTitle() {
        return this.noticeTitle;
    }

    /**
     * setter for noticeTitle.
     * 
     * @param aNoticeTitle noticeTitle
     */
    public void setNoticeTitle(final String aNoticeTitle) {
        this.noticeTitle = aNoticeTitle;
    }

    /**
     * getter for noticeContent.
     * 
     * @return noticeContent
     */
    public String getNoticeContent() {
        return this.noticeContent;
    }

    /**
     * setter for noticeContent.
     * 
     * @param aNoticeContent noticeContent
     */
    public void setNoticeContent(final String aNoticeContent) {
        this.noticeContent = aNoticeContent;
    }

    /**
     * getter for creater.
     * 
     * @return creater
     */
    public LoginUser getCreater() {
        return this.creater;
    }

    /**
     * setter for creater.
     * 
     * @param aCreater creater
     */
    public void setCreater(final LoginUser aCreater) {
        this.creater = aCreater;
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

    /**
     * getter for status.
     * 
     * @return status
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * setter for status.
     * 
     * @param aStatus status
     */
    public void setStatus(final Integer aStatus) {
        this.status = aStatus;
    }

}
