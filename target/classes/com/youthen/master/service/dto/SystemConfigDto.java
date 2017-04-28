package com.youthen.master.service.dto;

import javax.persistence.Column;
import com.youthen.framework.common.annotation.Dto;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Dto
public class SystemConfigDto extends MasterEntryDto {

    /**
     * 
     */
    private static final long serialVersionUID = -9077484361272089029L;

    private Long configId;

    /**
     * 密码强度设置 使用正则表达式进行校验
     */
    public String pwdValidtion;
    /**
     * 密码有效期 强制修改密码时间 天
     */
    public int pwdChangeDays;
    /**
     * 密码修改最小天数
     */
    public int pwdChangeMin;
    /**
     * 密码错误锁定用户
     */
    public int pwdErrTime;
    /**
     * 密码不能和以前多少次相同
     */
    public int pwdNoSame;
    /**
     * 客户图片设置
     */
    public String customerLogo;
    /**
     * 发送邮件服务器
     */
    public String smtpServer;

    /**
     * 发送邮件服务器端口号
     */
    public int smtpPort;

    /**
     * 上传文件格式列表
     */
    public String uploadFileFormat;

    /**
     * 上传文件格式列表
     */
    private String[] uploadFileFormatArr;

    /**
     * 上传文件大小限制
     */
    public long uploadFileMaxsize;
    /**
     * 报表表头图片
     */
    public String reportHeader;
    /**
     * 报表页面设置
     */
    public String reportFooter;
    /**
     * 最大用户数量
     */
    @Column(name = "MAX_USER")
    public int maxUser;
    /**
     * 自动保存时间设置 单位 秒
     */
    public int autoSaveTime;

    /**
     * 密码长度最大值
     */
    public int pwdLenMin;

    /**
     * 密码长度最小值
     */
    public int pwdLenMax;

    /**
     * 密码强度设置
     */
    public String[] pwdStyle;

    /**
     * getter for configId.
     * 
     * @return configId
     */
    public Long getConfigId() {
        return this.configId;
    }

    /**
     * setter for configId.
     * 
     * @param aConfigId configId
     */
    public void setConfigId(final Long aConfigId) {
        this.configId = aConfigId;
    }

    /**
     * Get value of pwdValidtion
     * 
     * @return pwdValidtion
     */
    public String getPwdValidtion() {
        return this.pwdValidtion;
    }

    /**
     * Set value of pwdValidtion
     * 
     * @param newPwdValidtion
     */
    public void setPwdValidtion(final String newPwdValidtion) {
        this.pwdValidtion = newPwdValidtion;
    }

    /**
     * Get value of pwdChangeDays
     * 
     * @return pwdChangeDays
     */
    public int getPwdChangeDays() {
        return this.pwdChangeDays;
    }

    /**
     * Set value of pwdChangeDays
     * 
     * @param newPwdChangeDays
     */
    public void setPwdChangeDays(final int newPwdChangeDays) {
        this.pwdChangeDays = newPwdChangeDays;
    }

    /**
     * Get value of pwdChangeMin
     * 
     * @return pwdChangeMin
     */
    public int getPwdChangeMin() {
        return this.pwdChangeMin;
    }

    /**
     * Set value of pwdChangeMin
     * 
     * @param newPwdChangeMin
     */
    public void setPwdChangeMin(final int newPwdChangeMin) {
        this.pwdChangeMin = newPwdChangeMin;
    }

    /**
     * Get value of pwdErrTime
     * 
     * @return pwdErrTime
     */
    public int getPwdErrTime() {
        return this.pwdErrTime;
    }

    /**
     * Set value of pwdErrTime
     * 
     * @param newPwdErrTime
     */
    public void setPwdErrTime(final int newPwdErrTime) {
        this.pwdErrTime = newPwdErrTime;
    }

    /**
     * Get value of pwdNoSame
     * 
     * @return pwdNoSame
     */
    public int getPwdNoSame() {
        return this.pwdNoSame;
    }

    /**
     * Set value of pwdNoSame
     * 
     * @param newPwdNoSame
     */
    public void setPwdNoSame(final int newPwdNoSame) {
        this.pwdNoSame = newPwdNoSame;
    }

    /**
     * Get value of customerLogo
     * 
     * @return customerLogo
     */
    public java.lang.String getCustomerLogo() {
        return this.customerLogo;
    }

    /**
     * Set value of customerLogo
     * 
     * @param newCustomerLogo
     */
    public void setCustomerLogo(final java.lang.String newCustomerLogo) {
        this.customerLogo = newCustomerLogo;
    }

    /**
     * Get value of smtpServer
     * 
     * @return smtpServer
     */
    public java.lang.String getSmtpServer() {
        return this.smtpServer;
    }

    /**
     * Set value of smtpServer
     * 
     * @param newSmtpServer
     */
    public void setSmtpServer(final java.lang.String newSmtpServer) {
        this.smtpServer = newSmtpServer;
    }

    /**
     * @return the smtpPort
     */
    public int getSmtpPort() {
        return this.smtpPort;
    }

    /**
     * Set value of smtpPort
     * 
     * @param newSmtpPort
     */
    public void setSmtpPort(final int newSmtpPort) {
        this.smtpPort = newSmtpPort;
    }

    /**
     * Get value of uploadFileFormat
     * 
     * @return uploadFileFormat
     */
    public java.lang.String getUploadFileFormat() {
        return this.uploadFileFormat;
    }

    /**
     * Set value of uploadFileFormat
     * 
     * @param newUploadFileFormat
     */
    public void setUploadFileFormat(final String newUploadFileFormat) {
        this.uploadFileFormat = newUploadFileFormat;
    }

    /**
     * Get value of uploadFileMaxsize
     * 
     * @return uploadFileMaxsize
     */
    public long getUploadFileMaxsize() {
        return this.uploadFileMaxsize;
    }

    /**
     * Set value of uploadFileMaxsize
     * 
     * @param newUploadFileMaxsize
     */
    public void setUploadFileMaxsize(final long newUploadFileMaxsize) {
        this.uploadFileMaxsize = newUploadFileMaxsize;
    }

    /**
     * Get value of reportHeader
     * 
     * @return reportHeader
     */
    public java.lang.String getReportHeader() {
        return this.reportHeader;
    }

    /**
     * Set value of reportHeader
     * 
     * @param newReportHeader
     */
    public void setReportHeader(final String newReportHeader) {
        this.reportHeader = newReportHeader;
    }

    /**
     * Get value of reportFooter
     * 
     * @return reportFooter
     */
    public java.lang.String getReportFooter() {
        return this.reportFooter;
    }

    /**
     * Set value of reportFooter
     * 
     * @param newReportFooter
     */
    public void setReportFooter(final String newReportFooter) {
        this.reportFooter = newReportFooter;
    }

    /**
     * Get value of maxUser
     * 
     * @return maxUser
     */
    public int getMaxUser() {
        return this.maxUser;
    }

    /**
     * Set value of maxUser
     * 
     * @param newMaxUser
     */
    public void setMaxUser(final int newMaxUser) {
        this.maxUser = newMaxUser;
    }

    /**
     * Get value of autoSaveTime
     * 
     * @return autoSaveTime
     */
    public int getAutoSaveTime() {
        return this.autoSaveTime;
    }

    /**
     * Set value of autoSaveTime
     * 
     * @param newAutoSaveTime
     */
    public void setAutoSaveTime(final int newAutoSaveTime) {
        this.autoSaveTime = newAutoSaveTime;
    }

    /**
     * getter for pwdLenMin.
     * 
     * @return pwdLenMin
     */
    public int getPwdLenMin() {
        return this.pwdLenMin;
    }

    /**
     * setter for pwdLenMin.
     * 
     * @param aPwdLenMin pwdLenMin
     */
    public void setPwdLenMin(final int aPwdLenMin) {
        this.pwdLenMin = aPwdLenMin;
    }

    /**
     * getter for pwdLenMax.
     * 
     * @return pwdLenMax
     */
    public int getPwdLenMax() {
        return this.pwdLenMax;
    }

    /**
     * setter for pwdLenMax.
     * 
     * @param aPwdLenMax pwdLenMax
     */
    public void setPwdLenMax(final int aPwdLenMax) {
        this.pwdLenMax = aPwdLenMax;
    }

    /**
     * getter for pwdStyle.
     * 
     * @return pwdStyle
     */
    public String[] getPwdStyle() {
        return this.pwdStyle;
    }

    /**
     * setter for pwdStyle.
     * 
     * @param aPwdStyle pwdStyle
     */
    public void setPwdStyle(final String[] aPwdStyle) {
        this.pwdStyle = aPwdStyle;
    }

    /**
     * getter for uploadFileFormatArr.
     * 
     * @return uploadFileFormatArr
     */
    public String[] getUploadFileFormatArr() {
        return this.uploadFileFormatArr;
    }

    /**
     * setter for uploadFileFormatArr.
     * 
     * @param aUploadFileFormatArr uploadFileFormatArr
     */
    public void setUploadFileFormatArr(final String[] aUploadFileFormatArr) {
        this.uploadFileFormatArr = aUploadFileFormatArr;
    }

    /**
     * @see com.youthen.master.service.dto.MasterEntryDto#getId()
     */

    @Override
    public Long getId() {
        return this.configId;
    }

}
