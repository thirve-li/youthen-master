// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.master.logic.RoleLogic;
import com.youthen.master.persistence.dao.RoleDao;
import com.youthen.master.persistence.dao.RoleFunctionDao;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.persistence.entity.RoleFunction;
import com.youthen.master.service.dto.RoleDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("roleLogic")
public class RoleLogicImpl extends EntityLogicImpl<RoleDto, Role> implements RoleLogic {

    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleFunctionDao roleFunctionDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleDto> selectCommon() throws ObjectNotFoundException {
        // final String hql = "FROM Role WHERE code not in ('QCAB','LAMB','MAMB','QCMB','LAKSHZ','MAKSHZ')";
        final String hql = "FROM Role order by code ";
        final List<Role> lst = this.roleDao.getByHql(hql);
        return converToDtoList(lst);
    }

    // 插入角色功能
    public void insertRoleFunction(final RoleDto aDto) throws DuplicateKeyException {
        RoleFunction foleFunction = null;
        final Long[] ids = aDto.getFunctionIds();
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                foleFunction = new RoleFunction();
                foleFunction.setFunctionId(ids[i]);
                foleFunction.setRoleId(aDto.getId());
                this.roleFunctionDao.insert(foleFunction);
            }
        }
    }

    // 删除角色功能
    public void deleteRoleFunction(final RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException {
        final List<RoleFunction> roleFunctionList =
                this.roleFunctionDao.getByHql("from RoleFunction where roleId=?", new Object[] {aDto.getId()});
        if (roleFunctionList.size() > 0) {
            for (final RoleFunction foleFunction : roleFunctionList) {
                this.roleFunctionDao.delete(foleFunction);
            }
        }

    }

    @Override
    public void updateRole(final RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException {

        final Role role = this.roleDao.getById(aDto.getId());
        BeanUtils.copyProperties(aDto, role);
        CommonEntityUtils.initializeCommonFields(role);
        role.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
        this.roleDao.update(role);
        this.deleteRoleFunction(aDto);
        this.insertRoleFunction(aDto);

    }

    @Override
    public void deleteRole(final RoleDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException {

        final Role role = this.roleDao.getById(aDto.getId());
        // BeanUtils.copyProperties(aDto, role);
        BeanUtils.copyNullableProperties(aDto, role);
        this.roleDao.delete(role);

        this.deleteRoleFunction(aDto);
    }

    @Override
    public void addRole(final RoleDto aDto) throws DuplicateKeyException, ObjectNotFoundException {

        final Role role = new Role();
        BeanUtils.copyNullableProperties(aDto, role);
        final Long roleId = (Long) this.roleDao.insert(role);
        aDto.setId(roleId);

        if (aDto.getId() != null) {
            this.insertRoleFunction(aDto);
        }

    }

    @Override
    public RoleDto getDtoInstance() {
        return new RoleDto();
    }

    @Override
    public EntityDaoImpl<Role> getDaoImpl() {
        return this.roleDao;
    }

    /**
     * @see com.youthen.framework.logic.EntityLogic#getEntityInstance()
     */

    @Override
    public Role getEntityInstance() {
        return new Role();
    }

    /**
     * @throws ObjectNotFoundException
     * @see com.youthen.master.logic.RoleLogic#getRoleByCompanyId(java.lang.Long)
     */

    @Override
    public List<RoleDto> getRoleByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {

        if (aCompanyId == null) {
            return this.converToDtoList(this.roleDao.selectAll());
        } else {
            return this.converToDtoList(this.roleDao.getRoleByCompanyId(aCompanyId));
        }
    }

    /**
     * @see com.youthen.master.logic.RoleLogic#getRoleByRecorderTypeId(java.lang.Long)
     */

    @Override
    public List<RoleDto> getRoleByRecorderTypeId(final Long aRecorderTypeId) throws ObjectNotFoundException {
        return this.converToDtoList(this.roleDao.getRoleByRecorderTypeId(aRecorderTypeId));
    }

    /**
     * @see com.youthen.master.logic.RoleLogic#getRoleList(com.youthen.master.service.dto.RoleDto)
     */

    @Override
    public List<RoleDto> getRoleList(final RoleDto aDto) {
        return this.roleDao.getRoleList(aDto);
    }

    @Override
    public int getRoleListCount(final RoleDto aDto) {
        return this.roleDao.getRoleListCount(aDto);
    }

    /**
     * @throws ObjectNotFoundException
     * @see com.youthen.master.logic.RoleLogic#validateCode(com.youthen.master.service.dto.RoleDto)
     */

    @Override
    public String validateCode(final RoleDto aDto) throws ObjectNotFoundException {
        String sbr = null;
        final List<Role> roleList = this.roleDao.selectAll();
        Role hasRole = new Role();
        if (aDto.getId() != null) {
            hasRole = this.roleDao.getById(aDto.getId());
        }
        for (final Role role : roleList) {
            if (role.getCode().equals(aDto.getCode()) && role.getId() != hasRole.getId()) {
                // ConfigFileHolder.setFilePath("limsMessages.properties");
                // sbr = ConfigFileHolder.getString("Msg170");

                final FieldSupportedMessage errorMessage = new FieldSupportedMessage("MST0001");
                // final Object[] parms = {changeMin};
                // errorMessage.format(parms);// 替换参数值
                sbr = errorMessage.getMesg(); // 取得编号为MST0001的properties中的信息
            }

        }
        return sbr;
    }

    /**
     * @see com.youthen.master.logic.RoleLogic#getRoleByCode(java.lang.String)
     */

    @Override
    public Role getRoleByCode(final String aRoleCode) {
        final String hql = "FROM Role WHERE code=?";
        final List<Role> lst = this.roleDao.getByHql(hql, new String[] {aRoleCode});

        if (lst != null && !lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

}
