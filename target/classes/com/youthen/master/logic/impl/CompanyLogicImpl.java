// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import javax.annotation.Resource;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.CompanyLogic;
import com.youthen.master.persistence.dao.CompanyDao;
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
@BusinessLogic("companyLogic")
public class CompanyLogicImpl extends EntityLogicImpl<CompanyDto, Company> implements CompanyLogic {

    @Resource
    private CompanyDao companyDao;

    /**
     * @see com.youthen.master.logic.CompanyLogic#add(com.youthen.master.service.dto.CompanyDto)
     */

    @Override
    public void add(final CompanyDto aDto) throws DuplicateKeyException, ObjectNotFoundException {
        final Company company = new Company();
        BeanUtils.copyProperties(aDto, company);
        this.companyDao.insert(company);

    }

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */

    @Override
    public CompanyDto getDtoInstance() {
        return new CompanyDto();
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */

    @Override
    public EntityDaoImpl<Company> getDaoImpl() {
        return this.companyDao;
    }

}
