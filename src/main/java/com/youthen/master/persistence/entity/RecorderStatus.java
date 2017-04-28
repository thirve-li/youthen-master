// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * 流程状态表。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class RecorderStatus extends AbstractCommonEntity {

    /**
     * S_UID
     */
    private static final long serialVersionUID = 288701712754427138L;

    // ID
    private Long id;

    // 状态编码
    private String statusCode;

    // 状态名称
    private String name;

    // 流程版本号
    private String recorderVersion;

    // 所属流程种类ID
    private Long recorderTypeId;

    // 流程状态组
    private Integer groupNum;

    private Long companyId;
    String createrId;
    Date createTime;

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
     * getter for statusCode.
     * 
     * @return statusCode
     */
    public String getStatusCode() {
        return this.statusCode;
    }

    /**
     * setter for statusCode.
     * 
     * @param aStatusCode statusCode
     */
    public void setStatusCode(final String aStatusCode) {
        this.statusCode = aStatusCode;
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
     * getter for recorderVersion.
     * 
     * @return recorderVersion
     */
    public String getRecorderVersion() {
        return this.recorderVersion;
    }

    /**
     * setter for recorderVersion.
     * 
     * @param aRecorderVersion recorderVersion
     */
    public void setRecorderVersion(final String aRecorderVersion) {
        this.recorderVersion = aRecorderVersion;
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
     * getter for groupNum
     * 
     * @return groupNum
     */
    public Integer getGroupNum() {
        return this.groupNum;
    }

    /**
     * setter for groupNum
     * 
     * @param aGroupNum
     */
    public void setGroupNum(final Integer aGroupNum) {
        this.groupNum = aGroupNum;
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
}
