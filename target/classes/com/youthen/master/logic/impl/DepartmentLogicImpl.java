// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.DepartmentLogic;
import com.youthen.master.persistence.dao.DepartmentDao;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.service.dto.DepartmentDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("departmentLogic")
@Transactional(rollbackFor = Throwable.class)
public class DepartmentLogicImpl extends EntityLogicImpl<DepartmentDto, Department> implements DepartmentLogic {

    @Resource
    private DepartmentDao departmentDao;

    @Override
    public List<DepartmentDto> getByParentId(final Long parentId) throws ObjectNotFoundException {
        return getDtoList(this.departmentDao.getByParentId(parentId));
    }

    private List<DepartmentDto> getDtoList(final List<Department> departmentList)
            throws ObjectNotFoundException {
        final List<DepartmentDto> departmentDtoList = new ArrayList<DepartmentDto>();
        for (int i = 0; i < departmentList.size(); i++) {
            final DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(departmentList.get(i), departmentDto);
            departmentDto.setChildrenDto(getByParentId(departmentDto.getId()));
            departmentDtoList.add(departmentDto);
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto specialUpdate(final DepartmentDto aDto) throws DuplicateKeyException {
        final Department e = getDaoImpl().getById(aDto.getId());
        BeanUtils.copyNullableProperties(aDto, e);
        return aDto;
    }

    /**
     * @see com.youthen.master.logic.DepartmentLogic#getByDepartmentName(java.lang.String)
     */

    @Override
    public List<DepartmentDto> getByDepartmentName(final String aName) throws ObjectNotFoundException {
        return getDtoList(this.departmentDao.getByDepartmentName(aName));
    }

    @Override
    public DepartmentDto getDtoInstance() {
        return new DepartmentDto();
    }

    @Override
    public EntityDaoImpl<Department> getDaoImpl() {
        return this.departmentDao;
    }

    @Override
    public List<DepartmentDto> getByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        final List<Department> departmentList = this.departmentDao.getByCompanyId(aCompanyId);

        final List<DepartmentDto> departmentDtoList = new ArrayList<DepartmentDto>();
        final Map<Long, List<DepartmentDto>> childrenDtoMap = new HashMap<Long, List<DepartmentDto>>();
        List<DepartmentDto> childrenDto = null;
        for (int i = 0; i < departmentList.size(); i++) {
            final DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(departmentList.get(i), departmentDto);
            final Long parentDepartmentId = departmentDto.getParentDepartmentId();
            if (parentDepartmentId != null && parentDepartmentId > 0) {
                childrenDto = childrenDtoMap.get(parentDepartmentId);
                if (childrenDto == null) {
                    childrenDto = new ArrayList<DepartmentDto>();
                }
                childrenDto.add(departmentDto);
                childrenDtoMap.put(parentDepartmentId, childrenDto);
            }
            departmentDtoList.add(departmentDto);
        }
        for (final DepartmentDto departmentDto : departmentDtoList) {
            childrenDto = childrenDtoMap.get(departmentDto.getId());
            if (childrenDto == null) {
                childrenDto = new ArrayList<DepartmentDto>();
            }
            departmentDto.setChildrenDto(childrenDto);
        }
        childrenDto = null;
        return departmentDtoList;
    }

    @Override
    public List<DepartmentDto> getChildrenByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        return getDtoList(this.departmentDao.getChildrenByCompanyId(aCompanyId));
    }

}
