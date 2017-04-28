// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.PageBean;
import com.youthen.framework.persistence.entity.CommonEntity;
import com.youthen.master.persistence.dao.MasterDataMantanceDao;
import com.youthen.master.service.MasterDataMantanceService;

/**
 * 。
 * 
 * @author
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "masterDataBaseService")
@Transactional(rollbackFor = Throwable.class)
public class MasterDataMantanceServiceImpl<T extends CommonEntity> implements MasterDataMantanceService {

    @Resource
    private MasterDataMantanceDao baseDao;

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#insert(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public Serializable insert(final CommonEntity aT) {
        try {
            return this.baseDao.insert(aT);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#setType(java.lang.Class)
     */

    @Override
    public void setType(final Class aClazz) {
        this.baseDao.setType(aClazz);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#selectAll()
     */

    @Override
    public List selectAll() {
        return this.baseDao.selectAll();
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#getById()
     */

    @Override
    public CommonEntity getById(final Serializable id) {
        return this.baseDao.getById(id);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#update(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void update(final CommonEntity aT) {
        this.baseDao.update(aT);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#specialUpdate(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void specialUpdate(final CommonEntity aT) {
        this.baseDao.specialUpdate(aT);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#querySql(java.lang.String)
     */

    @Override
    public List queryByCondition(final String condition) {
        return this.baseDao.getByHql(condition);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#delete(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void delete(final CommonEntity aT) {
        try {
            this.baseDao.delete(aT);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#deleteById(java.io.Serializable)
     */

    @Override
    public void deleteById(final Serializable aId) {
        delete(this.baseDao.getById(aId));
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#saveOrUpdate(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void saveOrUpdate(final CommonEntity aT) {
        this.baseDao.saveOrUpdate(aT);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#getByPageBean(com.youthen.framework.common.PageBean)
     */

    @Override
    @Transactional
    public PageBean<T> getByPageBean(final PageBean aPageBean) {
        try {
            return this.baseDao.getByPageBean(aPageBean);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#getByHql(java.lang.String, java.lang.Object[])
     */

    @Override
    public List<T> getByHql(final String aSql, final Object[] aParamValue) {
        return this.baseDao.getByHql(aSql, aParamValue);
    }

    /**
     * @see com.youthen.master.service.MasterDataMantanceService#getByHql(java.lang.String)
     */

    @Override
    public List queryByHql(final String aSql) {
        return this.baseDao.queryByHql(aSql);
    }
}
