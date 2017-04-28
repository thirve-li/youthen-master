package com.youthen.master.persistence.entity;

import java.io.Serializable;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class SystemConfig extends AbstractCommonEntity {

    private static final long serialVersionUID = 5120954427787021035L;

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
    public int maxUser;

    /**
     * 自动保存时间设置 单位 秒
     */
    public int autoSaveTime;

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
    public void setSmtpServer(final String newSmtpServer) {
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
     * @see com.youthen.framework.persistence.entity.CommonEntity#getId()
     */

    @Override
    public Serializable getId() {
        return this.configId;
    }
}
