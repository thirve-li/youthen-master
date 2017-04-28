// ============================================================
// Copyright(c) Soltoris Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.DepartmentLogic;
import com.youthen.master.persistence.dao.DepartmentDao;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.DepartmentDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "departmentService")
@Transactional(rollbackFor = Throwable.class)
public class DepartmentServiceImpl extends EntityServiceImpl<DepartmentDto, Department> implements DepartmentService {

    @Resource
    private DepartmentLogic departmentLogic;

    @Resource
    private LoginUserService loginUserService;

    @Autowired
    DepartmentDao dao;

    @Autowired
    private MasterDataMantanceService masterDataMantanceService;

    @Autowired
    private OsAudittrailService audittrailService;

    @Autowired
    KbnDao kbnDao;

    @Override
    public List<DepartmentDto> getByParentId(final Long parentId) throws ObjectNotFoundException {
        return this.departmentLogic.getByParentId(parentId);
    }

    @Override
    public EntityLogic<DepartmentDto, Department> getLogicImpl() {
        return this.departmentLogic;
    }

    @Override
    public List<DepartmentDto> getByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        return this.departmentLogic.getByCompanyId(aCompanyId);
    }

    @Override
    public List<DepartmentDto> getChildrenByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        return this.departmentLogic.getChildrenByCompanyId(aCompanyId);
    }

    @Override
    public DepartmentDto insert(final DepartmentDto aDto) {
        DepartmentDto deptDto = null;
        try {

            if (aDto.getId() == null) {

                deptDto = this.departmentLogic.insert(aDto);

            }
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
        return deptDto;

    }

    @Override
    public DepartmentDto update(final DepartmentDto aDto)
            throws DuplicateKeyException {
        return this.departmentLogic.update(aDto);

    }

    @Transactional
    @Override
    public void updateDepartment(final DepartmentDto aDto) {
        final Department department = this.dao.getById(aDto.getId());
        BeanUtils.copyNullableProperties(aDto, department);
        this.dao.update(department);

    }

    @Override
    @Transactional
    public DepartmentDto insertOrUpdate(final DepartmentDto aDto) throws DuplicateKeyException {
        if (aDto.getId() != null && aDto.getId() > 0) {

            final Department dept = this.dao.getById(aDto.getId());
            final Long oldStatus = dept.getStatus();

            if (oldStatus.longValue() == 1 && aDto.getStatus().longValue() == 0) {// 部门状态改为失效时，同时失效部门用户

                this.masterDataMantanceService.setType(LoginUser.class);
                final List<LoginUser> list =
                        this.masterDataMantanceService.queryByCondition(" departmentId= " + aDto.getId());

                final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "DELETED");

                for (final LoginUser user : list) {
                    user.setStatus(kbn);
                    user.setActionName("失效");
                    user.setChangedContent("[项目名称：用户状态  原值：" + user.getStatus().getNameCn() + " 新值：失效]");
                    user.setObjectName(user.getName());
                    this.masterDataMantanceService.update(user);
                }
            }

            // 部门修改审查追踪

            BeanUtils.copyAllNullableProperties(aDto, dept);
            if (oldStatus.longValue() == 1 && aDto.getStatus().longValue() == 0) {
                dept.setActionName("失效");
            } else {
                dept.setActionName(aDto.getId() == null ? "新增部门" : "修改部门");
            }
            this.masterDataMantanceService.setType(Department.class);
            this.masterDataMantanceService.update(dept);
            return aDto;

        }
        this.masterDataMantanceService.setType(Department.class);
        final Department dept = new Department();
        dept.setActionName("新增");
        dept.setObjectName(aDto.getName());
        BeanUtils.copyAllNullableProperties(aDto, dept);
        final Long deptId = (Long) this.masterDataMantanceService.insert(dept);
        return this.getById(deptId);
    }

    @Override
    public List<DepartmentDto> getByDepartmentName(final String aName) throws DuplicateKeyException,
            ObjectNotFoundException {
        return this.departmentLogic.getByDepartmentName(aName);
    }

}
