// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.AttachedLogic;
import com.youthen.master.persistence.dao.AttachedDao;
import com.youthen.master.persistence.entity.Attached;
import com.youthen.master.service.dto.AttachedDto;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("attachedLogic")
public class AttachedLogicImpl extends EntityLogicImpl<AttachedDto, Attached> implements AttachedLogic {

    @Autowired
    private AttachedDao attachedDao;

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */
    @Override
    public AttachedDto getDtoInstance() {
        return new AttachedDto();
    }

    /**
     * @throws OptimisticLockStolenException
     * @see com.youthen.workflow.logic.AttachedLogic#update(com.youthen.workflow.service.dto.AttachedDto)
     */
    @Override
    public AttachedDto saveOrUpdate(final AttachedDto aDto) throws DuplicateKeyException, ObjectNotFoundException,
            OptimisticLockStolenException {
        final Attached attached = new Attached();
        BeanUtils.copyProperties(aDto, attached);
        if (attached.getId() == null || attached.getId().longValue() <= 0L) {
            this.attachedDao.insert(attached);
        } else {
            this.attachedDao.update(attached);
        }
        BeanUtils.copyProperties(attached, aDto);
        return aDto;
    }

    @Override
    public Attached update(final Attached attached) throws Exception {
        if (attached.getId() == null || attached.getId().longValue() <= 0L) {
            this.attachedDao.insert(attached);
        } else {
            this.attachedDao.update(attached);
        }
        return attached;
    }

    /**
     * @see com.youthen.workflow.logic.AttachedLogic#findById(java.lang.Long)
     */
    @Override
    public AttachedDto findById(final Long aId) throws ObjectNotFoundException {
        final Attached attached = this.attachedDao.getById(aId);
        final AttachedDto dto = getDtoInstance();
        if (attached != null) {
            BeanUtils.copyNullableProperties(attached, dto);
            return dto;
        }
        return null;
    }

    /**
     * @see com.youthen.workflow.logic.AttachedLogic#deleteById(java.lang.Long)
     */
    @Override
    public void deleteById(final Long aId) throws OptimisticLockStolenException, ObjectNotFoundException {
        final Attached attached = this.attachedDao.getById(aId);
        if (attached != null) {
            this.attachedDao.delete(attached);
        }
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */
    @Override
    public EntityDaoImpl<Attached> getDaoImpl() {
        return this.attachedDao;
    }

}
