// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.List;
import javax.annotation.Resource;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.logic.RoleFunctionLogic;
import com.youthen.master.persistence.dao.RoleFunctionDao;
import com.youthen.master.persistence.entity.RoleFunction;
import com.youthen.master.service.dto.RoleFunctionDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("roleFunctionLogic")
public class RoleFunctionLogicImpl extends EntityLogicImpl<RoleFunctionDto, RoleFunction> implements RoleFunctionLogic {

    @Resource
    private RoleFunctionDao roleFunctionDao;

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */

    @Override
    public RoleFunctionDto getDtoInstance() {
        return new RoleFunctionDto();
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */

    @Override
    public EntityDaoImpl<RoleFunction> getDaoImpl() {
        return this.roleFunctionDao;
    }

    /**
     * @see com.youthen.master.logic.RoleFunctionLogic#getFunction()
     */

    @Override
    public Long[] getFunction(final Long aId) {

        final List<RoleFunction> RoleFunction =
                this.roleFunctionDao.getByHql("from RoleFunction where roleId=?", new Object[] {aId});

        Long[] Ids = null;
        if (RoleFunction != null) {
            Ids = new Long[RoleFunction.size()];
            int i = 0;
            for (final RoleFunction fF : RoleFunction) {
                Ids[i++] = fF.getFunctionId();
            }

        }
        return Ids;
    }

    /**
     * @see com.youthen.master.logic.RoleFunctionLogic#getFunctionByRoleId(java.lang.Long)
     */

    @Override
    public List<RoleFunction> getFunctionByRoleId(final Long aRoleId) {
        return this.roleFunctionDao.getByHql(aRoleId);
    }

}
