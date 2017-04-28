// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.KbnLogic;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.dto.KbnDto;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "kbnService")
@Transactional(rollbackFor = Throwable.class)
public class KbnServiceImpl implements KbnService {

    @Resource
    private KbnLogic kbnLogic;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Autowired
    KbnDao kbnDao;

    /**
     * @see com.youthen.master.service.KbnService#getKbn(java.lang.String, java.lang.String)
     */
    @Override
    public KbnDto getKbn(final String aType, final String aCode) {
        return this.kbnLogic.getKbn(aType, aCode);

    }

    /**
     * @see com.youthen.master.service.KbnService#getKbnListByType(java.lang.String)
     */
    @Override
    public List<KbnDto> getKbnListByType(final String aType) {
        return this.kbnLogic.getKbnListByType(aType);
    }

    /**
     * @see com.youthen.master.service.KbnService#getKbnListBy(com.youthen.master.service.dto.KbnDto)
     */
    @Override
    public List<KbnDto> getKbnListBy(final KbnDto aDto) {
        return this.kbnLogic.getKbnListBy(aDto);
    }

    /**
     * @see com.youthen.master.service.KbnService#getById(java.lang.Long)
     */

    @Override
    public KbnDto getById(final Long aId) {
        return this.kbnLogic.getById(aId);
    }

    /**
     * @see com.youthen.master.service.KbnService#getKbnList(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public List<KbnDto> getBigColum() {
        return this.kbnLogic.getBigColum();
    }

    /**
     * @see com.youthen.master.service.KbnService#getBigXiaoQuColum()
     */

    @Override
    public List<KbnDto> getBigXiaoQuColum() {
        return this.kbnLogic.getBigXiaoQuColum();
    }

    /**
     * @see com.youthen.master.service.KbnService#getByParentTypeId(java.lang.Long)
     */

    @Override
    public List<KbnDto> getByParentTypeId(final Long aId) {
        return this.kbnLogic.getByParentTypeId(aId);
    }

    /**
     * @see com.youthen.master.service.KbnService#update(com.youthen.master.service.dto.KbnDto)
     */

    @Override
    public void update(final KbnDto aDto) {
        final Kbn kbn = this.kbnDao.getById(aDto.getId());
        BeanUtils.copyAllNullableProperties(aDto, kbn);
        this.kbnDao.update(kbn);
    }

    /**
     * @see com.youthen.master.service.KbnService#insert(com.youthen.master.service.dto.KbnDto)
     */

    @Override
    public void insert(final KbnDto aDto) {
        final Kbn kbn = new Kbn();
        BeanUtils.copyAllNullableProperties(aDto, kbn);
        try {
            this.kbnDao.insert(kbn);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

}
