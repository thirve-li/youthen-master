// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import java.util.Date;
import com.youthen.framework.common.StringUtils;

/**
 * 。
 * 
 * @author DELL
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class MstCutomizedColDto extends MasterEntryDto {

    /**
     * 。
     */
    private static final long serialVersionUID = 4532639673535586290L;

    private Long id;
    private String tableName;
    private String type;
    private String objId;
    private String objName;
    private String customizedCol;
    private String createrId;
    private Date createTime;

    private String customizedCols[];

    /**
     * @see com.youthen.framework.persistence.entity.CommonEntity#getId()
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * getter for tableName.
     * 
     * @return tableName
     */
    @Override
    public String getTableName() {
        return this.tableName;
    }

    /**
     * setter for tableName.
     * 
     * @param aTableName tableName
     */
    @Override
    public void setTableName(final String aTableName) {
        this.tableName = aTableName;
    }

    /**
     * getter for type.
     * 
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * setter for type.
     * 
     * @param aType type
     */
    public void setType(final String aType) {
        this.type = aType;
    }

    /**
     * getter for objId.
     * 
     * @return objId
     */
    public String getObjId() {
        return this.objId;
    }

    /**
     * setter for objId.
     * 
     * @param aObjId objId
     */
    public void setObjId(final String aObjId) {
        this.objId = aObjId;
    }

    /**
     * getter for customizedCol.
     * 
     * @return customizedCol
     */
    public String getCustomizedCol() {
        return this.customizedCol;
    }

    /**
     * setter for customizedCol.
     * 
     * @param aCustomizedCol customizedCol
     */
    public void setCustomizedCol(final String aCustomizedCol) {
        this.customizedCol = aCustomizedCol;
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
     * getter for objName.
     * 
     * @return objName
     */
    public String getObjName() {
        return this.objName;
    }

    /**
     * setter for objName.
     * 
     * @param aObjName objName
     */
    public void setObjName(final String aObjName) {
        this.objName = aObjName;
    }

    /**
     * getter for customizedCols.
     * 
     * @return customizedCols
     */
    public String[] getCustomizedCols() {

        if (StringUtils.isNotEmpty(this.customizedCol)) {
            this.customizedCols = this.customizedCol.split(",");
        }

        return this.customizedCols;
    }

    /**
     * setter for customizedCols.
     * 
     * @param aCustomizedCols customizedCols
     */
    public void setCustomizedCols(final String[] aCustomizedCols) {
        this.customizedCols = aCustomizedCols;
    }

}
