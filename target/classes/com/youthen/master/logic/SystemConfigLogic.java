// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.service.dto.SystemConfigDto;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface SystemConfigLogic {

    SystemConfigDto findByConfigId(Long aConfigId) throws ObjectNotFoundException;

    void add(final SystemConfigDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

    void saveOrUpdate(SystemConfigDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException;

    void delete(Long aConfigId) throws ObjectNotFoundException, OptimisticLockStolenException;

    SystemConfigDto load() throws ObjectNotFoundException;

}
