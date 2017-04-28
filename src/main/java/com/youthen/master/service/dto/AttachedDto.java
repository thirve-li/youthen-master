// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.dto;

import java.sql.Blob;
import com.youthen.framework.common.annotation.Dto;
import com.youthen.framework.service.dto.BaseDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Dto
public class AttachedDto extends BaseDto {

    private static final long serialVersionUID = 3406671050897684514L;

    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件格式
     */
    private String fileType;

    /**
     * 文件内容
     */
    private Blob fileContent;

    private String recorderId;// 流程或功能ID
    private String recorderType;// 流程类型或功能类型
    private String sectionId;
    private String remark;// 上传文件
    private String code;// 附件编码

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
     * getter for filePath.
     * 
     * @return filePath
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * setter for filePath.
     * 
     * @param aFilePath filePath
     */
    public void setFilePath(final String aFilePath) {
        this.filePath = aFilePath;
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

    /**
     * getter for fileContent.
     * 
     * @return fileContent
     */
    public Blob getFileContent() {
        return this.fileContent;
    }

    /**
     * setter for fileContent.
     * 
     * @param aFileContent fileContent
     */
    public void setFileContent(final Blob aFileContent) {
        this.fileContent = aFileContent;
    }

    /**
     * getter for recorderId.
     * 
     * @return recorderId
     */
    public String getRecorderId() {
        return this.recorderId;
    }

    /**
     * setter for recorderId.
     * 
     * @param aRecorderId recorderId
     */
    public void setRecorderId(final String aRecorderId) {
        this.recorderId = aRecorderId;
    }

    /**
     * getter for recorderType.
     * 
     * @return recorderType
     */
    public String getRecorderType() {
        return this.recorderType;
    }

    /**
     * setter for recorderType.
     * 
     * @param aRecorderType recorderType
     */
    public void setRecorderType(final String aRecorderType) {
        this.recorderType = aRecorderType;
    }

    /**
     * getter for sectionId.
     * 
     * @return sectionId
     */
    public String getSectionId() {
        return this.sectionId;
    }

    /**
     * setter for sectionId.
     * 
     * @param aSectionId sectionId
     */
    public void setSectionId(final String aSectionId) {
        this.sectionId = aSectionId;
    }

    /**
     * getter for remark.
     * 
     * @return remark
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * setter for remark.
     * 
     * @param aRemark remark
     */
    public void setRemark(final String aRemark) {
        this.remark = aRemark;
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
     * @param aCode code
     */
    public void setCode(final String aCode) {
        this.code = aCode;
    }

}
