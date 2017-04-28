// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import javax.annotation.Resource;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.logic.FunctionLogic;
import com.youthen.master.persistence.dao.FunctionDao;
import com.youthen.master.persistence.entity.Function;
import com.youthen.master.service.dto.FunctionDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("functionLogic")
public class FunctionLogicImpl extends EntityLogicImpl<FunctionDto, Function> implements FunctionLogic {

    @Resource
    private FunctionDao functionDao;

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */

    @Override
    public FunctionDto getDtoInstance() {
        return new FunctionDto();
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */

    @Override
    public EntityDaoImpl<Function> getDaoImpl() {
        return this.functionDao;
    }

    /**
     * @see com.youthen.master.logic.FunctionLogic#getFunctionByCode(java.lang.String)
     */

    @Override
    public Function getFunctionByCode(final String aFunctioncd) {
        return this.functionDao.getFunctionByCode(aFunctioncd);
    }

}
