// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.service.EntityService;
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
public interface RoleFunctionService extends EntityService<RoleFunctionDto> {

    Long[] getFunction(final Long aLong);

    List<RoleFunction> getFunctionByRoleId(Long roleId);

}
