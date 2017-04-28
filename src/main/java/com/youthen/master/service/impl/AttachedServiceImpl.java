// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.AttachedLogic;
import com.youthen.master.persistence.dao.AttachedDao;
import com.youthen.master.persistence.entity.Attached;
import com.youthen.master.service.AttachedService;
import com.youthen.master.service.dto.AttachedDto;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service("attachedService")
public class AttachedServiceImpl implements AttachedService {

    @Autowired
    private AttachedLogic attachedLogic;

    @Autowired
    AttachedDao attachedDao;

    @Transactional
    @Override
    public AttachedDto update(final AttachedDto aDto) throws DuplicateKeyException, ObjectNotFoundException,
            OptimisticLockStolenException {
        return this.attachedLogic.update(aDto);
    }

    @Transactional
    @Override
    public Attached update(final Attached entity) throws Exception {
        return this.attachedLogic.update(entity);
    }

    @Override
    public AttachedDto findById(final Long aId) throws ObjectNotFoundException {
        return this.attachedLogic.findById(aId);
    }

    @Transactional
    @Override
    public void deleteById(final Long aId) throws OptimisticLockStolenException, ObjectNotFoundException {
        this.attachedLogic.deleteById(aId);
    }

    /**
     * @see com.youthen.workflow.service.AttachedService#getAttachedDtoList(java.lang.String)
     */
    @Override
    public List<AttachedDto> getAttachedDtoList(final String aIds) {
        final List<Attached> lst = this.attachedDao.getAttachedList(aIds);
        final List<AttachedDto> dtoLst = new ArrayList<AttachedDto>();
        if (lst != null) {
            for (final Attached entity : lst) {
                final AttachedDto dto = new AttachedDto();
                BeanUtils.copyProperties(entity, dto);
                BeanUtils.setNAProperty(dto);
                dtoLst.add(dto);
            }
        }

        return dtoLst;
    }
}
