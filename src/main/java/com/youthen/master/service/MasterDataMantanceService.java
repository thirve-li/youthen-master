// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.io.Serializable;
import java.util.List;
import com.youthen.framework.common.PageBean;
import com.youthen.framework.persistence.entity.CommonEntity;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface MasterDataMantanceService<T extends CommonEntity> {

    public void setType(final Class aClazz);

    public Serializable insert(final T aT);

    public List<T> selectAll();

    List<T> getByHql(String sql, Object[] paramValue);

    List<T> queryByHql(String sql);

    public T getById(Serializable id);

    public void update(T aT);

    void specialUpdate(final T aT);

    public void delete(T aT);

    public void deleteById(Serializable id);

    public void saveOrUpdate(T aT);

    public List<T> queryByCondition(String condition);

    PageBean<T> getByPageBean(final PageBean<T> aPageBean);

}
