package com.youthen.master.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import com.youthen.framework.common.AppMessage;
import com.youthen.framework.common.PageBean;
import com.youthen.framework.common.SimpleAppMessage;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.exception.ApplicationException;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.persistence.entity.CommonEntity;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.persistence.entity.OsAudittrail;

/**
 * DAO基类
 * 
 * @copyright
 * @author LiXin
 * @param <E>
 * @Revision
 * @date 2014-7-14
 */
@Repository("baseDao")
public class MasterDataMantanceDaoImpl<E extends CommonEntity> extends HibernateDaoSupport implements
        MasterDataMantanceDao<E> {

    private Class<E> clazz;

    private Map<String, Object> paramMap;

    /**
     * @see com.youthen.master.persistence.dao.MasterDataMantanceDao#setType(java.lang.Class)
     */

    @Override
    public void setType(final Class aClazz) {
        this.clazz = aClazz;
    }

    @Autowired
    public void setup(@Qualifier("sessionFactory") final SessionFactory aSessionFactory) {
        this.setSessionFactory(aSessionFactory);
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#update(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void update(final CommonEntity aEntity) {
        if (aEntity == null) {
            final AppMessage message = new SimpleAppMessage("XFW52002");
            throw new ApplicationException(message);
        }

        try {
            CommonEntityUtils.updateCommonFields(aEntity);
            getHibernateTemplate().update(aEntity);
            if (StringUtils.isEmpty(aEntity.getActionName())) {
                aEntity.setActionName("修改");
            }
            insertAudit(aEntity);
        } catch (final Exception e) {
            final AppMessage message = new FieldSupportedMessage("EFW52002").format(aEntity);
            throw new ApplicationException(message);
        }
    }

    /**
     * @see com.youthen.master.persistence.dao.MasterDataMantanceDao#specialUpdate(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void specialUpdate(final E aEntity) {
        if (aEntity == null) {
            final AppMessage message = new SimpleAppMessage("XFW52002");
            throw new ApplicationException(message);
        }

        try {
            getHibernateTemplate().update(aEntity);
        } catch (final Exception e) {
            final AppMessage message = new FieldSupportedMessage("EFW52002").format(aEntity);
            throw new ApplicationException(message);
        }
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#insert(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public Serializable insert(final CommonEntity aEntity) throws DuplicateKeyException {
        if (aEntity == null) {
            final AppMessage message = new SimpleAppMessage("XFW52002");
            throw new ApplicationException(message);
        }
        CommonEntityUtils.initializeCommonFields(aEntity);

        try {
            final Serializable id = getHibernateTemplate().save(aEntity);
            if (StringUtils.isEmpty(aEntity.getActionName())) {
                aEntity.setActionName("新增");
            }
            insertAudit(aEntity);
            return id;
        } catch (final DataAccessException e) {
            e.printStackTrace();
            final AppMessage message = new FieldSupportedMessage("EFW52002").format(aEntity);
            throw new DuplicateKeyException(message, e);
        }
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#delete(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void delete(final CommonEntity aEntity) throws ObjectNotFoundException, OptimisticLockStolenException {
        if (aEntity == null) {
            final AppMessage message = new SimpleAppMessage("XFW52002");
            throw new ApplicationException(message);
        }

        getHibernateTemplate().delete(aEntity);

        if (StringUtils.isNotEmpty(aEntity.getObjectName())) {
            insertAudit(aEntity);
        }
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#selectAll()
     */

    @Override
    public List selectAll() {
        List<E> all = new ArrayList<E>();
        all = getHibernateTemplate().loadAll(this.clazz);
        return all;
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#getById(java.io.Serializable)
     */

    @Override
    public E getById(final Serializable id) {
        if (id == null) {
            final AppMessage message = new SimpleAppMessage("XFW52002").format(id);
            throw new ApplicationException(message);
        }

        final E t = getHibernateTemplate().get(this.clazz, id);

        final String hql =
                "from " + OsAudittrail.class.getName()
                        + " where beOptedObjectId='" + id + "'"
                        + " and tableName='" + this.clazz.getName() + "' order by createTime desc";

        final List list = getSession().createQuery(hql).list();
        t.setLogList(list);
        return t;
    }

    /**
     * @see com.youthen.framework.persistence.dao.MasterDataBaseDao#getByHql(java.lang.String)
     */

    @Override
    public List getByHql(final String condition) {
        String hql = "from " + this.clazz.getName() + " where 1=1";
        if (StringUtils.isNotEmpty(condition)) {
            hql += " and " + condition;
        }
        return getSession().createQuery(hql).list();
    }

    /**
     * @see com.youthen.master.persistence.dao.MasterDataMantanceDao#saveOrUpdate(com.youthen.framework.persistence.entity.CommonEntity)
     */

    @Override
    public void saveOrUpdate(final CommonEntity aEntity) {
        getHibernateTemplate().saveOrUpdate(aEntity);
        if (StringUtils.isEmpty(aEntity.getActionName())) {
            aEntity.setActionName("修改");
        }
        insertAudit(aEntity);
    }

    private void insertAudit(final CommonEntity aEntity) {
        this.setType(OsAudittrail.class);
        final OsAudittrail odt = new OsAudittrail();
        odt.setActionName(aEntity.getActionName());
        odt.setBeOptedObjectId(aEntity.getId().toString());
        odt.setObjectName(aEntity.getObjectName());
        odt.setTableName(aEntity.getClass().getName());
        odt.setReason(aEntity.getReason());
        odt.setUpdTime(CommonEntityUtils.getSystime());
        odt.setSysAndArea(aEntity.getSysAndArea());
        odt.setUpdId(aEntity.getUpdId());
        if (StringUtils.isNotEmpty(aEntity.getChangedContent())) {
            odt.setChangedContent(aEntity.getChangedContent());
        } else {
            odt.setChangedContent(aEntity.getActionName() + aEntity.getObjectName());
        }
        if (StringUtils.isEmpty(odt.getCreaterId())) {
            odt.setCreaterId(CommonEntityUtils.getUpdId());
        }
        if (StringUtils.isEmpty(odt.getUpdId())) {
            odt.setUpdId(CommonEntityUtils.getUpdId());
        }

        odt.setCompanyId(CommonEntityUtils.getCompanyId());
        odt.setCreateTime(Calendar.getInstance().getTime());

        if (odt.getBeOptedObjectId() != null && StringUtils.isNotEmpty(odt.getObjectName())) {
            getHibernateTemplate().save(odt);
        }
    }

    @SuppressWarnings("unchecked")
    public PageBean<E> getByPageBean(final PageBean<E> pageBean) throws Exception {
        // 获取主键名
        String countField = null;
        final Field[] fields = this.clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (!field.getName().equals("serialVersionUID")) {
                countField = field.getName();
                break;
            }
        }
        // 查询条件所在map
        final Map<String, String> whereMap = pageBean.getWhereMap();
        // 查询总数量hql
        final StringBuffer countHql =
                new StringBuffer("select count(" + countField + ") from " + this.clazz.getName()
                        + " tmpObj where 1=1 " + pageBean.getWhereHql());
        // 查询实体hql
        final StringBuffer hql =
                new StringBuffer("from " + this.clazz.getName() + " tmpObj where 1=1 " + pageBean.getWhereHql());
        // 查询条件hql
        final StringBuffer whereHql = new StringBuffer();
        // 组HQL字符串
        for (final String key : whereMap.keySet()) {
            final String[] keyArray = key.split("_");
            if (keyArray.length > 1 && pageBean.getOperatorMap().get(keyArray[0]) != null
                    && StringUtils.isNotEmpty(whereMap.get(key))) {
                whereHql.append(" and tmpObj."
                        + key.replace(keyArray[0] + "_", "")
                        + " "
                        + pageBean.getOperatorMap().get(keyArray[0]) + ":" + key.replaceAll("\\.", ""));
            }
        }
        countHql.append(whereHql);
        // 排序
        if (StringUtils.isNotEmpty(pageBean.getSortColumnName())) {
            if (StringUtils.isEmpty(pageBean.getAsc())) pageBean.setAsc("asc");
            whereHql.append(" order by " + pageBean.getSortColumnName());
            if (pageBean.getAsc().toLowerCase().equals("true") || pageBean.getAsc().toLowerCase().equals("asc")) {
                whereHql.append(" ASC");
            }
            if (pageBean.getAsc().toLowerCase().equals("false") || pageBean.getAsc().toLowerCase().equals("desc")) {
                whereHql.append(" DESC");
            }
            // 防止数据库中SortColumn重复导致出现重复数据，漏数据
            whereHql.append(",id DESC");
        }
        hql.append(whereHql);

        final Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        final Query query = session.createQuery(hql.toString());
        final Query countQuery = session.createQuery(countHql.toString());

        // 参数类型转换
        this.setParamMap(whereMap);
        // 给查询条件放值
        for (final String key : whereMap.keySet()) {
            final String[] keyArray = key.split("_");
            if (keyArray.length > 1 && pageBean.getOperatorMap().get(keyArray[0]) != null
                    && StringUtils.isNotEmpty(whereMap.get(key))) {
                final Object paramObj = this.paramMap.get(key);
                query.setParameter(key.replaceAll("\\.", ""), paramObj);
                countQuery.setParameter(key.replaceAll("\\.", ""), paramObj);
            }
        }
        final int pageSize = pageBean.getPageSize().intValue();
        if (pageSize < 1000) {
            query.setFirstResult((pageBean.getCurrent().intValue() - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        final List list = countQuery.list();
        if (!list.isEmpty()) {
            pageBean.setRecordCount(new Integer(list.get(0).toString()));
            pageBean.setRecordList(query.list());
        }

        return pageBean;
    }

    private void setParamMap(final Map<String, String> aParamMap) throws Exception {
        this.paramMap = new HashMap<String, Object>();
        final Iterator<String> it = aParamMap.keySet().iterator();
        while (it.hasNext()) {
            final String key = it.next();
            final String[] keyArray = key.split("_");
            if (new PageBean<E>().getOperatorMap().get(keyArray[0]) != null) {
                String fieldName = keyArray[1];
                Class tmpClass = this.clazz;
                if (fieldName.indexOf(".") > 0) {
                    final String[] tmpFileNameArray = fieldName.split("\\.");
                    final String tmpFileName = tmpFileNameArray[0];
                    tmpClass = tmpClass.getDeclaredField(tmpFileName).getType();
                    fieldName = tmpFileNameArray[1];
                }
                final Field field = tmpClass.getDeclaredField(fieldName);
                final String type = field.getType().getName().toString();
                String valStr = aParamMap.get(key);
                if (StringUtils.isNotEmpty(valStr)) {
                    if (type.equals("java.lang.String")) {
                        if (key.startsWith("like_")) {
                            valStr = "%" + valStr + "%";
                        }
                        this.paramMap.put(key, valStr);
                    } else if (type.equals("java.lang.Long")) {
                        this.paramMap.put(key, new Long(valStr));
                    } else if (type.equals("java.lang.Integer")) {
                        this.paramMap.put(key, new Integer(valStr));
                    } else if (type.equals("java.util.Date")) {
                        if (key.startsWith("date1_")) {
                            this.paramMap.put(key,
                                    CommonUtils.stringToDate(valStr + " 00:00:01", "yyyy-MM-dd HH:mm:ss"));
                        } else if (key.startsWith("date2_")) {
                            this.paramMap.put(key,
                                    CommonUtils.stringToDate(valStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
                        }
                    }
                }
            }

        }
    }

    /**
     * @see com.youthen.master.persistence.dao.MasterDataMantanceDao#getByHql(java.lang.String,
     *      java.lang.Object[])
     */

    @Override
    public List<E> getByHql(final String aSql, final Object[] aParamValue) {
        final List<E> list = getHibernateTemplate().find(aSql, aParamValue);
        return list;
    }

    /**
     * @see com.youthen.master.persistence.dao.MasterDataMantanceDao#queryByHql(java.lang.String)
     */
    @Override
    public List<E> queryByHql(final String aSql) {
        return getHibernateTemplate().find(aSql);
    }

}
