// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.EntityLogic;
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
public interface CompanyLogic extends EntityLogic<CompanyDto, Company> {

    void add(final CompanyDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

}
