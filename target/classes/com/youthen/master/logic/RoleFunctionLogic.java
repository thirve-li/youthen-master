// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import java.util.List;
import com.youthen.framework.logic.EntityLogic;
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
public interface RoleFunctionLogic extends EntityLogic<RoleFunctionDto, RoleFunction> {

    Long[] getFunction(final Long aL);

    List<RoleFunction> getFunctionByRoleId(Long aRoleId);
}
