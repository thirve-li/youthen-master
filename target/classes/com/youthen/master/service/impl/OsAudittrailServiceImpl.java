// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.master.logic.OsAudittrailLogic;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.OsAudittrailDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "osAudittrailService")
@Transactional(rollbackFor = Throwable.class)
public class OsAudittrailServiceImpl extends EntityServiceImpl<OsAudittrailDto, OsAudittrail> implements
        OsAudittrailService {

    @Resource
    private OsAudittrailLogic osAudittrailLogic;

    @Resource
    protected LoginUserService loginUserService;

    @Override
    public List<OsAudittrailDto> getOsAudittrailList(final OsAudittrailDto aDto) {
        return this.osAudittrailLogic.getOsAudittrailList(aDto);
    }

    @Override
    public int getOsAudittrailCount(final OsAudittrailDto aDto) {
        return this.osAudittrailLogic.getOsAudittrailCount(aDto);
    }

    @Override
    @Transactional
    public void add(final OsAudittrailDto aDto) throws DuplicateKeyException, ObjectNotFoundException {
        this.osAudittrailLogic.add(aDto);
    }

    @Override
    public EntityLogic<OsAudittrailDto, OsAudittrail> getLogicImpl() {
        return this.osAudittrailLogic;
    }

    /**
     * @see com.youthen.master.service.OsAudittrailService#getAllOsAudittrailList(com.youthen.master.service.dto.OsAudittrailDto)
     */

    @Override
    public List<OsAudittrailDto> getAllOsAudittrailList(final OsAudittrailDto aDto) {
        return this.osAudittrailLogic.getAllOsAudittrailList(aDto);
    }

    @Override
    public OsAudittrailDto insert(final OsAudittrailDto aEntity) throws DuplicateKeyException {
        final String updId = CommonEntityUtils.getUpdId();
        if (aEntity.getCreater() == null && updId != null) {
            aEntity.setCreater(this.loginUserService.getUserByUserId(updId));
        }
        return super.insert(aEntity);
    }

}
