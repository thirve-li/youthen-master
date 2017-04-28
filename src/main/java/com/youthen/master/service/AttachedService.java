// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.persistence.entity.Attached;
import com.youthen.master.service.dto.AttachedDto;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface AttachedService {

    public AttachedDto update(final AttachedDto aDto) throws DuplicateKeyException, ObjectNotFoundException,
            OptimisticLockStolenException;

    public Attached update(final Attached entity) throws Exception;

    public AttachedDto findById(Long id) throws ObjectNotFoundException;

    public void deleteById(Long id) throws OptimisticLockStolenException, ObjectNotFoundException;

    public List<AttachedDto> getAttachedDtoList(String ids);

}
