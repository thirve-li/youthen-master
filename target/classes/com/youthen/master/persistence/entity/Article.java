// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 。
 * 文章管理
 * 
 * @author XS
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class Article extends AbstractCommonEntity {

    /**
     * 。
     */
    private static final long serialVersionUID = -1393066067391786371L;

    private Long id;

    private Integer status;
    private String title;
    private String content;
    private Date createTime;

    private Long bigColumnId;
    private Kbn bigColumn;
    private Long smallColumnId;
    private Kbn smallColumn;

    private LoginUser creater;

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
     * getter for title.
     * 
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * setter for title.
     * 
     * @param aTitle title
     */
    public void setTitle(final String aTitle) {
        this.title = aTitle;
    }

    /**
     * getter for content.
     * 
     * @return content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * setter for content.
     * 
     * @param aContent content
     */
    public void setContent(final String aContent) {
        this.content = aContent;
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
     * getter for bigColumnId.
     * 
     * @return bigColumnId
     */
    public Long getBigColumnId() {
        return this.bigColumnId;
    }

    /**
     * setter for bigColumnId.
     * 
     * @param aBigColumnId bigColumnId
     */
    public void setBigColumnId(final Long aBigColumnId) {
        this.bigColumnId = aBigColumnId;
    }

    /**
     * getter for bigColumn.
     * 
     * @return bigColumn
     */
    public Kbn getBigColumn() {
        return this.bigColumn;
    }

    /**
     * setter for bigColumn.
     * 
     * @param aBigColumn bigColumn
     */
    public void setBigColumn(final Kbn aBigColumn) {
        this.bigColumn = aBigColumn;
    }

    /**
     * getter for smallColumnId.
     * 
     * @return smallColumnId
     */
    public Long getSmallColumnId() {
        return this.smallColumnId;
    }

    /**
     * setter for smallColumnId.
     * 
     * @param aSmallColumnId smallColumnId
     */
    public void setSmallColumnId(final Long aSmallColumnId) {
        this.smallColumnId = aSmallColumnId;
    }

    /**
     * getter for smallColumn.
     * 
     * @return smallColumn
     */
    public Kbn getSmallColumn() {
        return this.smallColumn;
    }

    /**
     * setter for smallColumn.
     * 
     * @param aSmallColumn smallColumn
     */
    public void setSmallColumn(final Kbn aSmallColumn) {
        this.smallColumn = aSmallColumn;
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

}
