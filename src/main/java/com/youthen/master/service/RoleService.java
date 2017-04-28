// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.service.EntityService;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.dto.RoleDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface RoleService extends EntityService<RoleDto> {

    void updateRole(RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException, DuplicateKeyException;

    void addRole(RoleDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

    void deleteRole(RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException;

    List<RoleDto> getRoleByCompanyId(Long companyId) throws ObjectNotFoundException;

    List<RoleDto> getRoleByRecorderTypeId(Long recorderTypeId) throws ObjectNotFoundException;

    List<RoleDto> getRoleList(RoleDto aDto);

    int getRoleListCount(RoleDto aDto);

    String validateCode(RoleDto aDto) throws ObjectNotFoundException;

    Role getRoleByCode(String roleCode);

    List<RoleDto> selectCommon() throws ObjectNotFoundException;

}
