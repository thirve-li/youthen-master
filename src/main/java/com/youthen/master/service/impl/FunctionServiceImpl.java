// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.FunctionLogic;
import com.youthen.master.persistence.dao.FunctionDao;
import com.youthen.master.persistence.entity.Function;
import com.youthen.master.service.FunctionService;
import com.youthen.master.service.dto.FunctionDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "functionService")
@Transactional(rollbackFor = Throwable.class)
public class FunctionServiceImpl extends EntityServiceImpl<FunctionDto, Function> implements FunctionService {

    @Resource
    private FunctionLogic functionLogic;

    @Resource
    private FunctionDao functionDao;

    /**
     * @see com.youthen.framework.service.impl.EntityServiceImpl#getLogicImpl()
     */

    @Override
    public EntityLogic<FunctionDto, Function> getLogicImpl() {
        return this.functionLogic;
    }

    @Override
    public Function getFunctionByCode(final String aFunctioncd) {
        return this.functionLogic.getFunctionByCode(aFunctioncd);
    }

    /**
     * @see com.youthen.master.service.FunctionService#save(com.youthen.master.service.dto.FunctionDto)
     */

    @Override
    public void save(final FunctionDto aFuncDto) {
        final Function func = new Function();

        try {
            BeanUtils.copyAllProperties(aFuncDto, func);
            if (aFuncDto.getId() == null) {
                this.functionDao.insert(func);
            } else {
                this.functionDao.specialUpdate(func);
            }
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

}
