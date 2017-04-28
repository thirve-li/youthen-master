// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.presentation.action.AbstractAjaxAction;
import com.youthen.framework.service.SQLService;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/comm")
@Controller
@ExecAuthority(functioncd = "1")
public class MessageAjaxAction extends AbstractAjaxAction {

    private String key;

    private String tableName;
    private String queryCondition;
    private String oderBy;

    @Autowired
    private SQLService sqlService;

    public Object getList() {
        return this.sqlService.getList(this.tableName, this.queryCondition, this.oderBy);
    }

    @Override
    protected Object doExecute(final Object aArgs) throws Exception {
        return null;
    }

    public String getResourceBundle() {
        String value = "";
        if (this.key != null && !"".equals(this.key)) {
            final FieldSupportedMessage errorMessage = new FieldSupportedMessage(this.key);
            value = errorMessage.getMesg();
        }
        return value;
    }

    /**
     * getter for key.
     * 
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * setter for key.
     * 
     * @param aKey key
     */
    public void setKey(final String aKey) {
        this.key = aKey;
    }

    /**
     * getter for tableName.
     * 
     * @return tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * setter for tableName.
     * 
     * @param aTableName tableName
     */
    public void setTableName(final String aTableName) {
        this.tableName = aTableName;
    }

    /**
     * getter for queryCondition.
     * 
     * @return queryCondition
     */
    public String getQueryCondition() {
        return this.queryCondition;
    }

    /**
     * setter for queryCondition.
     * 
     * @param aQueryCondition queryCondition
     */
    public void setQueryCondition(final String aQueryCondition) {
        this.queryCondition = aQueryCondition;
    }

    /**
     * getter for oderBy.
     * 
     * @return oderBy
     */
    public String getOderBy() {
        return this.oderBy;
    }

    /**
     * setter for oderBy.
     * 
     * @param aOderBy oderBy
     */
    public void setOderBy(final String aOderBy) {
        this.oderBy = aOderBy;
    }

}
