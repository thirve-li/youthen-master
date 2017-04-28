// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import javax.annotation.Resource;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.DemoTestLogic;
import com.youthen.master.persistence.dao.DemoTestDao;
import com.youthen.master.persistence.entity.DemoTest;
import com.youthen.master.service.dto.DemoTestDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("demoTestLogic")
public class DemoTestLogicImpl extends EntityLogicImpl<DemoTestDto, DemoTest> implements DemoTestLogic {

    @Resource
    private DemoTestDao demoTestDao;

    @Override
    public void addUser(final DemoTestDto demoDto) throws DuplicateKeyException {
        final DemoTest demo = new DemoTest();
        BeanUtils.copyNullableProperties(demoDto, demo);
        this.demoTestDao.insert(demo);

    }

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */

    @Override
    public DemoTestDto getDtoInstance() {
        return new DemoTestDto();
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */

    @Override
    public EntityDaoImpl<DemoTest> getDaoImpl() {
        return this.demoTestDao;
    }

}
