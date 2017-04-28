// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.persistence.entity.CommonEntity;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.MstKbnService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.util.SystemConst;

/**
 * 。
 * 
 * @author Lixin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "mstKbnService")
@Transactional(rollbackFor = Throwable.class)
public class MstKbnServiceImpl<T extends CommonEntity> implements MstKbnService {

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Autowired
    KbnDao kbnDao;

    @Autowired
    private OsAudittrailService audittrailService;

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#addKbn(com.youthen.KbnDto.service.dto.CmsMstKbnDto)
     */

    @Override
    @Transactional
    public void addKbn(final KbnDto aDto) {
        final Kbn kbn = new Kbn();
        BeanUtils.copyAllNullableProperties(aDto, kbn);
        kbn.setStatus(1L);
        kbn.setObjectName(kbn.getNameCn());

        if (SystemConst.CUSTOMER_NAME.equalsIgnoreCase("999")) {
            final String force = aDto.getForce() == null ? " " : aDto.getForce();
            final String notForce = aDto.getNotForce() == null ? " " : aDto.getNotForce();
            kbn.setNameEn(force + "," + notForce);
        }

        this.masterDataMantanceService.setType(Kbn.class);
        this.masterDataMantanceService.insert(kbn);
    }

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#editKbn(com.youthen.KbnDto.service.dto.CmsMstKbnDto)
     */

    @Override
    @Transactional
    public void editKbn(final KbnDto aDto) {

        this.masterDataMantanceService.setType(Kbn.class);
        final Kbn kbn = (Kbn) this.masterDataMantanceService.getById(aDto.getId());
        BeanUtils.copyAllNullableProperties(aDto, kbn);
        kbn.setObjectName(kbn.getNameCn());
        kbn.setParentType(null);
        if (aDto == null || (aDto != null && StringUtils.isEmpty(aDto.getActionName()))) {
            if (kbn.getStatus() == 0) {
                kbn.setActionName("失效");
            } else {
                kbn.setActionName("修改");
            }
        }

        if (SystemConst.CUSTOMER_NAME.equalsIgnoreCase("999")) {
            final String force = aDto.getForce() == null ? " " : aDto.getForce();
            final String notForce = aDto.getNotForce() == null ? " " : aDto.getNotForce();
            kbn.setNameEn(force + "," + notForce);
        }

        this.masterDataMantanceService.update(kbn);

    }

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#getKbn(java.lang.String, java.lang.String)
     */

    @Override
    public KbnDto getKbn(final String aType, final String aCode) {
        this.masterDataMantanceService.setType(Kbn.class);

        final List<Kbn> list =
                this.masterDataMantanceService.queryByCondition("type='" + aType + "'" + " and code='" + aCode + "'");
        if (CollectionUtils.isNotEmpty(list)) {
            final Kbn kbn = list.get(0);
            final KbnDto kbnDto = new KbnDto();
            BeanUtils.copyAllNullableProperties(kbn, kbnDto);
            return kbnDto;
        }

        return null;
    }

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#getKbnById(java.lang.String, java.lang.String)
     */

    @Override
    public KbnDto getKbnById(final Long id) {
        this.masterDataMantanceService.setType(Kbn.class);
        final Kbn kbn = (Kbn) this.masterDataMantanceService.getById(id);
        final KbnDto kbnDto = new KbnDto();
        BeanUtils.copyAllNullableProperties(kbn, kbnDto);

        return kbnDto;
    }

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#getKbnListByType(java.lang.String)
     */

    @Override
    public List<KbnDto> getKbnListByType(final String aType) {
        this.masterDataMantanceService.setType(Kbn.class);

        final List<Kbn> list =
                this.masterDataMantanceService.queryByCondition("type='" + aType + "' and status=1");
        final List<KbnDto> dtolist = new ArrayList<KbnDto>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (final Kbn kbn : list) {
                final KbnDto kbnDto = new KbnDto();
                BeanUtils.copyAllNullableProperties(kbn, kbnDto);
                dtolist.add(kbnDto);
            }
        }

        return dtolist;
    }

    /**
     * @see com.youthen.MstKbnService.workflow.service.CmsMstKbnService#list(com.youthen.KbnDto.service.dto.CmsMstKbnDto)
     */

    @Override
    public List<KbnDto> list(KbnDto aDto) {
        this.masterDataMantanceService.setType(Kbn.class);

        if (aDto == null) {
            aDto = new KbnDto();
        }
        String condition = " from Kbn where 1=1";

        boolean flag = false;

        if (aDto.getCompanyId() != null) {
            condition += " and companyId ='" + aDto.getCompanyId() + "'";
            flag = true;
        }

        if (StringUtils.isNotEmpty(aDto.getNameCn())) {
            condition += " and nameCn ='" + aDto.getNameCn().trim() + "'";
            flag = true;
        }
        if (StringUtils.isNotEmpty(aDto.getCode())) {
            condition += " and code ='" + aDto.getCode() + "'";
            flag = true;
        }
        if (StringUtils.isNotEmpty(aDto.getType())) {
            condition += " and type ='" + aDto.getType() + "'";
            flag = true;
        }
        if (aDto.getStatus() != null) {
            condition += " and status ='" + aDto.getStatus() + "'";
            flag = true;
        }
        if (aDto.getParentTypeId() != null) {
            condition += " and parentType.id ='" + aDto.getParentTypeId() + "'";
            flag = true;
        }

        if (StringUtils.isNotEmpty(aDto.getBigTypeId())) {
            condition += " and parentType.parentType.id ='" + aDto.getBigTypeId() + "'";
            flag = true;
        }

        condition += " order by parentType.id ";
        final List<Kbn> list = this.kbnDao.getByHql(condition);
        final List<KbnDto> dtolist = new ArrayList<KbnDto>();

        if (flag == false) {
            return dtolist;
        }

        if (CollectionUtils.isNotEmpty(list)) {
            for (final Kbn kbn : list) {
                final KbnDto kbnDto = new KbnDto();
                if (kbn.getParentType() == null && kbn.getParentTypeId() != null) {
                    final Kbn parent = this.kbnDao.getById(kbn.getParentTypeId());
                    kbnDto.setParentType(parent);
                }

                BeanUtils.copyAllNullableProperties(kbn, kbnDto);
                kbnDto.setLogList(null);
                dtolist.add(kbnDto);
            }
        }

        return dtolist;
    }

    /**
     * @see com.youthen.master.service.MstKbnService#isNeedPlan()
     */

    @Override
    public boolean isCmsNeedPlan() {

        this.masterDataMantanceService.setType(Kbn.class);

        final List<Kbn> list =
                this.masterDataMantanceService.queryByCondition("type='CMS_INS_IS_NEED_PLAN'");

        if (CollectionUtils.isNotEmpty(list)) {
            final Kbn kbn = list.get(0);
            if ("0".equals(kbn.getCode())) {
                return false;
            }
            if ("1".equals(kbn.getCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.youthen.master.service.MstKbnService#isOurQuali()
     */

    @Override
    public boolean isOurQuali() {
        this.masterDataMantanceService.setType(Kbn.class);
        final List<Kbn> list = this.masterDataMantanceService.queryByCondition("type='CMS_EMPLOYEE_IS_OUR'");
        if (CollectionUtils.isNotEmpty(list)) {
            final Kbn kbn = list.get(0);
            if ("0".equals(kbn.getCode())) {
                return false;
            }
            if ("1".equals(kbn.getCode())) {
                return true;
            }
        }
        return false;
    }

}
