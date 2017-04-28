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
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class OsAudittrail extends AbstractCommonEntity {

    private static final long serialVersionUID = -7776920400273896894L;

    private Long id;
    private String beOptedObjectId;
    private Long companyId;
    private LoginUser creater;
    private String createrId;
    private Date createTime;
    /**
     * 系统和区域：值为4位，前两位为系统位(从10开始)，后两位为区域位(从10开始)
     * 比如：1010(10(QMS)10(处理列表区域))，为QMS系统待处理列表区域
     */
    private int sysAndArea;

    /**
     * getter for beOptedObjectId.
     * 
     * @return beOptedObjectId
     */
    public String getBeOptedObjectId() {
        return this.beOptedObjectId;
    }

    /**
     * setter for beOptedObjectId.
     * 
     * @param aBeOptedObjectId beOptedObjectId
     */
    public void setBeOptedObjectId(final String aBeOptedObjectId) {
        this.beOptedObjectId = aBeOptedObjectId;
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
     * getter for serialversionuid.
     * 
     * @return serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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
     * @see com.youthen.framework.persistence.entity.CommonEntity#getId()
     */

    @Override
    public Long getId() {
        return this.id;
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
     * getter for sysAndArea.
     * 
     * @return sysAndArea
     */
    public int getSysAndArea() {
        return this.sysAndArea;
    }

    /**
     * setter for sysAndArea.
     * 
     * @param aSysAndArea sysAndArea
     */
    public void setSysAndArea(final int aSysAndArea) {
        this.sysAndArea = aSysAndArea;
    }
}
