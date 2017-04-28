package com.youthen.master.common;

import com.youthen.framework.common.AppMessage;
import com.youthen.framework.common.exception.BusinessException;

public class BusinessCheckException extends BusinessException {

    /**
     * 。
     * 
     * @param aMessage
     */
    public BusinessCheckException(final AppMessage aMessage) {
        super(aMessage);

    }

    /**
     * 。
     */
    private static final long serialVersionUID = 1L;

}
