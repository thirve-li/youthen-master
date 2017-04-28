// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.logic.SystemConfigLogic;
import com.youthen.master.service.SystemConfigService;
import com.youthen.master.service.dto.SystemConfigDto;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "systemConfigService")
@Transactional(rollbackFor = Throwable.class)
public class SystemConfigServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigLogic systemConfigLogic;

    /**
     * @see com.youthen.master.service.SystemConfigService#findByConfigId(java.lang.Long)
     */

    @Override
    public SystemConfigDto findByConfigId(final Long aConfigId) throws ObjectNotFoundException {
        return this.systemConfigLogic.findByConfigId(aConfigId);
    }

    /**
     * @see com.youthen.master.service.SystemConfigService#add(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public void add(final SystemConfigDto aDto) throws BusinessCheckException {
        try {
            this.systemConfigLogic.add(aDto);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.SystemConfigService#saveOrUpdate(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public void saveOrUpdate(final SystemConfigDto aDto) throws BusinessCheckException {
        try {
            this.systemConfigLogic.saveOrUpdate(aDto);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (final OptimisticLockStolenException e) {
            e.printStackTrace();
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.SystemConfigService#find(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public SystemConfigDto find(final SystemConfigDto aConditionDto) throws BusinessCheckException {
        return null;
    }

    /**
     * @see com.youthen.master.service.SystemConfigService#delete(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public void delete(final SystemConfigDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException {
        this.systemConfigLogic.delete(aDto.getConfigId());
    }

    /**
     * @see com.youthen.master.service.SystemConfigService#load()
     */

    @Override
    public SystemConfigDto load() throws ObjectNotFoundException {
        return this.systemConfigLogic.load();
    }

}
