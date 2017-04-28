// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.master.logic.RoleLogic;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.dto.RoleDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "roleService")
@Transactional(rollbackFor = Throwable.class)
public class RoleServiceImpl extends EntityServiceImpl<RoleDto, Role> implements RoleService {

    @Resource
    private RoleLogic roleLogic;

    @Override
    public EntityLogic<RoleDto, Role> getLogicImpl() {
        return this.roleLogic;
    }

    @Override
    public List<RoleDto> selectCommon() throws ObjectNotFoundException {
        return this.roleLogic.selectCommon();
    }

    @Override
    public void updateRole(final RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException {
        this.roleLogic.updateRole(aDto);
    }

    @Override
    public void addRole(final RoleDto aDto) throws DuplicateKeyException, ObjectNotFoundException {
        this.roleLogic.addRole(aDto);
    }

    @Override
    public void deleteRole(final RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException {
        this.roleLogic.deleteRole(aDto);
    }

    @Override
    public List<RoleDto> getRoleByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        return this.roleLogic.getRoleByCompanyId(aCompanyId);
    }

    @Override
    public List<RoleDto> getRoleByRecorderTypeId(final Long aRecorderTypeId) throws ObjectNotFoundException {
        return this.roleLogic.getRoleByRecorderTypeId(aRecorderTypeId);
    }

    /**
     * @see com.youthen.master.service.RoleService#getRoleList(com.youthen.master.service.dto.RoleDto)
     */

    @Override
    public List<RoleDto> getRoleList(final RoleDto aDto) {
        return this.roleLogic.getRoleList(aDto);
    }

    /**
     * @see com.youthen.master.service.RoleService#getRoleListCount(com.youthen.master.service.dto.RoleDto)
     */

    @Override
    public int getRoleListCount(final RoleDto aDto) {
        return this.roleLogic.getRoleListCount(aDto);
    }

    /**
     * @see com.youthen.master.service.RoleService#validateCode(com.youthen.master.service.dto.RoleDto)
     */

    @Override
    public String validateCode(final RoleDto aDto) throws ObjectNotFoundException {
        return this.roleLogic.validateCode(aDto);
    }

    /**
     * @see com.youthen.master.service.RoleService#getRoleByCode(java.lang.String)
     */

    @Override
    public Role getRoleByCode(final String aRoleCode) {
        return this.roleLogic.getRoleByCode(aRoleCode);
    }

}
