// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.entity;

import java.sql.Blob;
import java.util.Date;
import com.youthen.framework.persistence.entity.AbstractCommonEntity;

/**
 * @author Administrator
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class EmailLog extends AbstractCommonEntity {

    private static final long serialVersionUID = 4993996534380633590L;

    private Long id;

    /**
     * 收件地址、收件人账号、中文名
     */
    private String receiverAddr;
    private String receiverEn;
    private String receiverCn;

    private LoginUser receiver;

    public LoginUser getReceiver() {
        return this.receiver;
    }

    public void setReceiver(final LoginUser aReceiver) {
        this.receiver = aReceiver;
    }

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件附件
     */
    private Blob attachment;

    /**
     * 发送日期
     */
    private Date sendDate;

    /**
     * 发送状态：-1：发送失败, 0：未发送,1：发送成功
     */
    private int sendStatus;

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
     * getter for subject.
     * 
     * @return subject
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * setter for subject.
     * 
     * @param aSubject subject
     */
    public void setSubject(final String aSubject) {
        this.subject = aSubject;
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
     * getter for attachment.
     * 
     * @return attachment
     */
    public Blob getAttachment() {
        return this.attachment;
    }

    /**
     * setter for attachment.
     * 
     * @param aAttachment attachment
     */
    public void setAttachment(final Blob aAttachment) {
        this.attachment = aAttachment;
    }

    /**
     * getter for sendDate.
     * 
     * @return sendDate
     */
    public Date getSendDate() {
        return this.sendDate;
    }

    /**
     * setter for sendDate.
     * 
     * @param aSendDate sendDate
     */
    public void setSendDate(final Date aSendDate) {
        this.sendDate = aSendDate;
    }

    /**
     * getter for sendStatus.
     * 
     * @return sendStatus
     */
    public int getSendStatus() {
        return this.sendStatus;
    }

    /**
     * setter for sendStatus.
     * 
     * @param aSendStatus sendStatus
     */
    public void setSendStatus(final int aSendStatus) {
        this.sendStatus = aSendStatus;
    }

    /**
     * getter for receiverAddr.
     * 
     * @return receiverAddr
     */
    public String getReceiverAddr() {
        return this.receiverAddr;
    }

    /**
     * setter for receiverAddr.
     * 
     * @param aReceiverAddr receiverAddr
     */
    public void setReceiverAddr(final String aReceiverAddr) {
        this.receiverAddr = aReceiverAddr;
    }

    /**
     * getter for receiverEn.
     * 
     * @return receiverEn
     */
    public String getReceiverEn() {
        return this.receiverEn;
    }

    /**
     * setter for receiverEn.
     * 
     * @param aReceiverEn receiverEn
     */
    public void setReceiverEn(final String aReceiverEn) {
        this.receiverEn = aReceiverEn;
    }

    /**
     * getter for receiverCn.
     * 
     * @return receiverCn
     */
    public String getReceiverCn() {
        return this.receiverCn;
    }

    /**
     * setter for receiverCn.
     * 
     * @param aReceiverCn receiverCn
     */
    public void setReceiverCn(final String aReceiverCn) {
        this.receiverCn = aReceiverCn;
    }

}
