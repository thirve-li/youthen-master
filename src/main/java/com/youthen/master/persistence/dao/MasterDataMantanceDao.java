package com.youthen.master.persistence.dao;

import java.io.Serializable;
import java.util.List;
import com.youthen.framework.common.PageBean;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.persistence.entity.CommonEntity;

/**
 * DAO基类
 * 
 * @copyright
 * @author LiXin
 * @Revision
 * @date 2010-3-26
 */
public interface MasterDataMantanceDao<E extends CommonEntity> {

    void specialUpdate(final E aEntity);

    void setType(final Class<E> aClazz);

    /**
     * 。
     * 
     * @param aEntity
     * @throws ObjectNotFoundException
     * @throws OptimisticLockStolenException
     */
    void saveOrUpdate(final E aEntity);

    /**
     * 。
     * 
     * @param aEntity
     * @throws ObjectNotFoundException
     * @throws OptimisticLockStolenException
     */
    void update(final E aEntity);

    /**
     * 。
     * 
     * @param aEntity
     * @throws DuplicateKeyException
     */
    Serializable insert(E aEntity) throws DuplicateKeyException;

    /**
     * 。
     * 
     * @param aEntity
     * @throws ObjectNotFoundException
     * @throws OptimisticLockStolenException
     */
    void delete(E aEntity) throws ObjectNotFoundException, OptimisticLockStolenException;

    /**
     * 。
     * 
     * @return
     * @throws ObjectNotFoundException
     */
    List<E> selectAll();

    /**
     * 。
     * 
     * @param id
     * @return
     */
    public E getById(final Serializable id);

    public List<E> getByHql(final String condition);

    List<E> getByHql(final String aSql, final Object[] aParamValue);

    List<E> queryByHql(final String aSql);

    public PageBean<E> getByPageBean(final PageBean<E> pageBean) throws Exception;

}
