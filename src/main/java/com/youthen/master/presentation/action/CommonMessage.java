package com.youthen.master.presentation.action;

import org.apache.commons.lang.StringUtils;
import com.youthen.framework.common.AppMessage;
import com.youthen.framework.common.exception.BusinessCheckException;
import com.youthen.framework.common.exception.BusinessException;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.master.common.BsCheckMessage;

public class CommonMessage {

    public static void addError(final BaseAction aAction, final String aPrefix, final BusinessException aE) {
        for (final AppMessage message : aE.getAppMessages()) {
            if (aE instanceof BusinessCheckException) {
                final BsCheckMessage bsmessage = (BsCheckMessage) message;

                if (bsmessage.getErrorFields() != null && bsmessage.getErrorFields().length > 0) {

                    // 变红设定
                    for (final String fieldName : bsmessage.getErrorFields()) {

                        String actualFieldName = fieldName;

                        if (StringUtils.isNotBlank(aPrefix)) {
                            actualFieldName = aPrefix + "." + fieldName;
                        }

                        aAction.addFieldError(actualFieldName, bsmessage.getMesg());
                    }
                } else {
                    aAction.addActionError(message.getMesg());
                }
            } else {
                aAction.addActionError(message.getMesg());
            }
        }
    }
}
