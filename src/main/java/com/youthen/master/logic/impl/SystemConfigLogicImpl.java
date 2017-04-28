// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.persistence.dao.SystemConfigDao;
import com.youthen.master.persistence.entity.Password;
import com.youthen.master.persistence.entity.SystemConfig;
import com.youthen.master.service.dto.SystemConfigDto;
import com.youthen.master.util.PasswordValidate;
import com.youthen.master.util.SystemConst;

/**
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("systemConfigLogic")
public class SystemConfigLogicImpl implements com.youthen.master.logic.SystemConfigLogic {

    @Resource
    private SystemConfigDao systemConfigDao;

    /**
     * @see com.youthen.master.logic.SystemConfigLogic#findByConfigId(java.lang.Long)
     */

    @Override
    public SystemConfigDto findByConfigId(final Long aConfigId) throws ObjectNotFoundException {

        final SystemConfigDto configDto = new SystemConfigDto();
        final SystemConfig systemConfig = this.systemConfigDao.getById(aConfigId);
        BeanUtils.copyProperties(systemConfig, configDto);
        return configDto;
    }

    /**
     * @see com.youthen.master.logic.SystemConfigLogic#add(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public void add(final SystemConfigDto aDto) throws DuplicateKeyException, ObjectNotFoundException {
        final SystemConfig aConfig = new SystemConfig();
        BeanUtils.copyProperties(aDto, aConfig);
        final Password password = new Password();
        password.init();
        password.setMaxLen(aDto.getPwdLenMax());
        password.setMinLen(aDto.getPwdLenMin());
        password.changeIsRequired(aDto.getPwdStyle());
        aConfig.setPwdValidtion(PasswordValidate.createXml(password));

        // 上传文件格式
        aConfig.setUploadFileFormat(SystemConst.UPLOAD_FILE_FORMAT_SPLIT +
                CommonUtils.arrayToString(aDto.getUploadFileFormatArr(), SystemConst.UPLOAD_FILE_FORMAT_SPLIT));
        this.systemConfigDao.insert(aConfig);
    }

    /**
     * @throws DuplicateKeyException
     * @see com.youthen.master.logic.SystemConfigLogic#saveOrUpdate(com.youthen.master.service.dto.SystemConfigDto)
     */

    @Override
    public void saveOrUpdate(final SystemConfigDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException {
        final SystemConfig systemConfig = this.systemConfigDao.getById(aDto.getId());
        BeanUtils.copyNullableProperties(aDto, systemConfig);
        final Password password = new Password();
        password.init();
        password.setMaxLen(aDto.getPwdLenMax());
        password.setMinLen(aDto.getPwdLenMin());
        password.changeIsRequired(aDto.getPwdStyle());
        systemConfig.setPwdValidtion(PasswordValidate.createXml(password));
        // 上传文件格式
        systemConfig.setUploadFileFormat(SystemConst.UPLOAD_FILE_FORMAT_SPLIT +
                CommonUtils.arrayToString(aDto.getUploadFileFormatArr(), SystemConst.UPLOAD_FILE_FORMAT_SPLIT));
        CommonEntityUtils.initializeCommonFields(systemConfig);
        systemConfig.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
        this.systemConfigDao.update(systemConfig);
    }

    /**
     * @see com.youthen.master.logic.SystemConfigLogic#delete(java.lang.Long)
     */

    @Override
    public void delete(final Long aConfigId) throws ObjectNotFoundException, OptimisticLockStolenException {
        final SystemConfig systemConfig = this.systemConfigDao.getById(aConfigId);
        this.systemConfigDao.delete(systemConfig);
    }

    /**
     * @see com.youthen.master.logic.SystemConfigLogic#load()
     */

    @Override
    public SystemConfigDto load() throws ObjectNotFoundException {
        final List<SystemConfig> systemConfigList = this.systemConfigDao.selectAll();
        final SystemConfigDto systemConfigDto = new SystemConfigDto();
        if (systemConfigList != null && systemConfigList.size() > 0) {
            BeanUtils.copyProperties(systemConfigList.get(systemConfigList.size() - 1), systemConfigDto);
        }
        return systemConfigDto;
    }
}
