// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.persistence.entity.Function;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("functionDao")
public class FunctionDao extends EntityDaoImpl<Function> {

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(Function.class);
    }

    @SuppressWarnings("unchecked")
    public Function getFunctionByCode(final String aFunctioncd) {
        final String hql =
                "from Function where code= '" + aFunctioncd + "'";
        final List<Function> functionList = this.getByHql(hql);
        if (functionList != null && functionList.size() > 0) {
            return functionList.get(0);
        }
        return null;
    }

}
