// ============================================================
// Copyright(c) Soltoris Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.service.EntityService;
import com.youthen.master.service.dto.DepartmentDto;

/**
 * @author chenxh
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface DepartmentService extends EntityService<DepartmentDto> {

    List<DepartmentDto> getByParentId(Long parentId) throws ObjectNotFoundException;

    List<DepartmentDto> getByCompanyId(Long companyId) throws ObjectNotFoundException;

    List<DepartmentDto> getChildrenByCompanyId(Long companyId) throws ObjectNotFoundException;

    void updateDepartment(DepartmentDto aDto);

    DepartmentDto insertOrUpdate(DepartmentDto aDto) throws DuplicateKeyException;

    List<DepartmentDto> getByDepartmentName(final String name) throws DuplicateKeyException, ObjectNotFoundException;

}
