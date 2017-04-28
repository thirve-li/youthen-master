// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.master.logic.RoleFunctionLogic;
import com.youthen.master.persistence.entity.RoleFunction;
import com.youthen.master.service.RoleFunctionService;
import com.youthen.master.service.dto.RoleFunctionDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "roleFunctionService")
@Transactional(rollbackFor = Throwable.class)
public class RoleFunctionServiceImpl extends EntityServiceImpl<RoleFunctionDto, RoleFunction> implements
        RoleFunctionService {

    @Resource
    private RoleFunctionLogic roleFunctionLogic;

    /**
     * @see com.youthen.framework.service.impl.EntityServiceImpl#getLogicImpl()
     */

    @Override
    public EntityLogic<RoleFunctionDto, RoleFunction> getLogicImpl() {
        return this.roleFunctionLogic;
    }

    /**
     * @see com.youthen.master.service.RoleFunctionService#getFunction(java.lang.Long)
     */

    @Override
    public Long[] getFunction(final Long aLong) {
        return this.roleFunctionLogic.getFunction(aLong);
    }

    /**
     * @see com.youthen.master.service.RoleFunctionService#getFunctionByRoleId(java.lang.Long)
     */

    @Override
    public List<RoleFunction> getFunctionByRoleId(final Long roleId) {
        return this.roleFunctionLogic.getFunctionByRoleId(roleId);
    }

}
