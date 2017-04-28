// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.OsAudittrailLogic;
import com.youthen.master.persistence.dao.OsAudittrailDao;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.service.dto.OsAudittrailDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("osAudittrailLogic")
@Transactional(rollbackFor = Throwable.class)
public class OsAudittrailLogicImpl extends EntityLogicImpl<OsAudittrailDto, OsAudittrail> implements OsAudittrailLogic {

    @Resource
    private OsAudittrailDao osAudittrailDao;

    // 分页查询，获取流程的审查追踪列表
    @Override
    public List<OsAudittrailDto> getOsAudittrailList(final OsAudittrailDto aDto) {
        final List<OsAudittrail> log = this.osAudittrailDao.getOsAudittrailList(aDto);
        return this.converToDtoList(log);
    }

    @Override
    public int getOsAudittrailCount(final OsAudittrailDto aDto) {
        return this.osAudittrailDao.getOsAudittrailCount(aDto);
    }

    @Override
    public List<OsAudittrailDto> converToDtoList(final List<OsAudittrail> entityList) {
        final List<OsAudittrailDto> dtoList = new ArrayList<OsAudittrailDto>();
        if (entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                final OsAudittrailDto d = new OsAudittrailDto();
                if (entityList.get(i) != null) {
                    BeanUtils.copyProperties(entityList.get(i), d);
                    BeanUtils.setNAProperty(d);
                    dtoList.add(d);
                }
            }
        }
        return dtoList;
    }

    @Override
    public void add(final OsAudittrailDto aDto) throws DuplicateKeyException, ObjectNotFoundException {
        final OsAudittrail osAudittrail = new OsAudittrail();
        BeanUtils.copyAllNullableProperties(aDto, osAudittrail);
        if (aDto.getBeOptedObjectId() != null) {
            osAudittrail.setBeOptedObjectId(aDto.getBeOptedObjectId().toString());
        }
        this.osAudittrailDao.insert(osAudittrail);
    }

    @Override
    public OsAudittrailDto getDtoInstance() {
        return new OsAudittrailDto();
    }

    @Override
    public EntityDaoImpl<OsAudittrail> getDaoImpl() {
        return this.osAudittrailDao;
    }

    /**
     * @see com.youthen.master.logic.OsAudittrailLogic#getAllOsAudittrailList(com.youthen.master.service.dto.OsAudittrailDto)
     */

    @Override
    public List<OsAudittrailDto> getAllOsAudittrailList(final OsAudittrailDto aDto) {
        final List<OsAudittrail> log = this.osAudittrailDao.getAllOsAudittrailList(aDto);
        return this.converToDtoList(log);
    }
}
