// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.service.dto.SystemConfigDto;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface SystemConfigService {

    SystemConfigDto findByConfigId(Long aConfigId) throws ObjectNotFoundException;

    void add(SystemConfigDto aDto) throws BusinessCheckException;

    void saveOrUpdate(SystemConfigDto aDto) throws BusinessCheckException;

    SystemConfigDto find(SystemConfigDto aConditionDto) throws BusinessCheckException;

    void delete(SystemConfigDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException;

    SystemConfigDto load() throws ObjectNotFoundException;

}
