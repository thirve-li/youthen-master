// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.service.dto.CompanyDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface CompanyService {

    void add(CompanyDto aDto) throws BusinessCheckException;

    CompanyDto find(CompanyDto aConditionDto) throws BusinessCheckException;

    CompanyDto update(CompanyDto aDto);

    CompanyDto getById(Long companyId) throws ObjectNotFoundException;

    List<CompanyDto> selectAll();

    List<Company> getByHql(String hql);

    boolean isCompanyExisted(String companyName);

    boolean isCompanyCodeExisted(String companyCode);

    void del(CompanyDto aDto) throws BusinessCheckException;
}
