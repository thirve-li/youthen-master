package com.youthen.master.common;

import org.apache.commons.lang.reflect.FieldUtils;
import com.youthen.framework.common.fields.FieldSupportedMessage;

/**
 * 业务校验用Class。
 * 
 * @author LiXin
 * @version $Revision: $<br>
 *          $Date: 2014-07-11 $
 */
public class BsCheckMessage extends FieldSupportedMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 变红项目。
     */
    private String[] errorFields;

    public BsCheckMessage(final String aCode) {
        super(aCode);
    }

    /**
     * getter for errorFields.
     * 
     * @return errorFields
     */
    public String[] getErrorFields() {
        return this.errorFields;
    }

    /**
     * setter for errorFields.
     * 
     * @param aErrorFields errorFields
     */
    public void setErrorFields(final String[] aErrorFields) {
        this.errorFields = aErrorFields;
    }

    @Override
    public BsCheckMessage format(final Object... aObjs) {
        super.format(aObjs);
        return this;
    }

    /**
     * 格式变换
     * 
     * <pre>
     * </pre>
     * 
     * @param aFieldSupportedMessage {@link FieldSupportedMessage}
     * @return message
     */
    public BsCheckMessage format(final FieldSupportedMessage aFieldSupportedMessage) {

        try {
            final Object[] params = (Object[]) FieldUtils.readField(aFieldSupportedMessage, "params", true);
            FieldUtils.writeField(this, "code", aFieldSupportedMessage.getCode(), true);
            super.format(params);
            return this;
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
