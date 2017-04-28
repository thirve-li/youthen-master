// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import java.util.List;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.EntityLogic;
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
public interface DepartmentLogic extends EntityLogic<DepartmentDto, Department> {

    List<DepartmentDto> getByParentId(final Long parentId) throws ObjectNotFoundException;

    List<DepartmentDto> getByCompanyId(final Long companyId) throws ObjectNotFoundException;

    List<DepartmentDto> getChildrenByCompanyId(final Long companyId) throws ObjectNotFoundException;

    List<DepartmentDto> getByDepartmentName(final String name) throws ObjectNotFoundException;;
}
