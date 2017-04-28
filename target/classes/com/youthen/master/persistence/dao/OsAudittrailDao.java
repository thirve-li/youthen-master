// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.persistence.entity.CommonEntity;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.OsAudittrailDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("osAudittrailDao")
public class OsAudittrailDao extends EntityDaoImpl<OsAudittrail> {

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Resource
    protected LoginUserService loginUserService;

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(OsAudittrail.class);
    }

    public List<OsAudittrail> getOsAudittrailList(final OsAudittrailDto aDto) {
        final String hql = getQuerySql(aDto);
        final Query query = this.getSession().createQuery(hql.toString());
        query.setFirstResult((aDto.getGotoPage() - 1) * aDto.getPageSize());
        query.setMaxResults(aDto.getPageSize());

        return query.list();
    }

    private String getQuerySql(final OsAudittrailDto aDto) {
        final StringBuffer hql = new StringBuffer();
        hql.append(" from OsAudittrail where 1 = 1 ");
        if (aDto.getCompanyId() != null) {
            hql.append(" and companyId= " + aDto.getCompanyId());
        }
        if (StringUtils.isNotEmpty(aDto.getTableName())) {
            hql.append(" and tableName= '" + aDto.getTableName() + "'");
        }

        if (!StringUtils.isEmpty(aDto.getBeOptedObjectId())) {
            hql.append(" and beOptedObjectId ='" + aDto.getBeOptedObjectId() + "'");
        }

        if (!StringUtils.isEmpty(aDto.getActionName())) {
            hql.append(" and actionName ='" + aDto.getActionName() + "'");
        }

        if (!StringUtils.isEmpty(aDto.getChangedContent())) {
            hql.append(" and changedContent like '%" + aDto.getChangedContent() + "%'");
        }

        if (aDto.getSysAndArea() > 0) {
            if (aDto.getSysAndArea() < 100) {
                hql.append(" and ((sysAndArea>=" + aDto.getSysAndArea() + "00" + " and sysAndArea<="
                        + aDto.getSysAndArea() + "99) or sysAndArea=0)");
            } else {
                hql.append(" and (sysAndArea=" + aDto.getSysAndArea() + " or sysAndArea=0)");
            }
        }

        if (aDto.getCreater() != null && aDto.getCreater().getName() != null && !aDto.getCreater().getName().isEmpty()) {
            hql.append(" and creater.name like '%" + aDto.getCreater().getName() + "%' ");
        }

        if (aDto.getObjectName() != null && !aDto.getObjectName().isEmpty()) {
            hql.append(" and objectName like '%" + aDto.getObjectName() + "%' ");
        }
        if (aDto.getUpdTime() != null && !aDto.getUpdTime().isEmpty()) {
            hql.append(aDto.getUpdTime());
        }

        hql.append(" order by updTime desc");
        return hql.toString();
    }

    public int getOsAudittrailCount(final OsAudittrailDto aDto) {
        final String hql = getQuerySql(aDto);
        final Query query = this.getSession().createQuery(hql);

        return query.list().size();
    }

    @SuppressWarnings("unchecked")
    public List<OsAudittrail> getAllOsAudittrailList(final OsAudittrailDto aDto) {
        final String hql = getQuerySql(aDto);
        final Query query = this.getSession().createQuery(hql);

        return query.list();
    }

    @Override
    public Serializable insert(final OsAudittrail aEntity) throws DuplicateKeyException {
        final String updId = CommonEntityUtils.getUpdId();
        if (aEntity.getCreater() == null && updId != null) {
            aEntity.setCreater(this.loginUserService.getUserByUserId(updId));
        }
        if (StringUtils.isEmpty(aEntity.getChangedContent()) && StringUtils.isNotEmpty(aEntity.getObjectName())
                && StringUtils.isNotEmpty(aEntity.getActionName())) {
            aEntity.setChangedContent(aEntity.getObjectName() + "被" + aEntity.getActionName());
        }
        return super.insert(aEntity);
    }

    public void insertByCommonEntity(final CommonEntity aEntity)
            throws Exception {
        final OsAudittrail audit = new OsAudittrail();
        audit.setObjectName(aEntity.getObjectName());
        audit.setSectionId(aEntity.getSectionId());
        audit.setActionName(aEntity.getActionName());
        audit.setReason(aEntity.getReason());
        audit.setCompanyId(CommonEntityUtils.getCompanyId());
        audit.setSysAndArea(aEntity.getSysAndArea());
        audit.setUpdTime(aEntity.getUpdTime());
        audit.setUpdId(aEntity.getUpdId());
        audit.setCreaterId(aEntity.getUpdId());
        if (StringUtils.isNotEmpty(aEntity.getUpdTime())) {
            audit.setCreateTime(DateFormatUtils.parse("yyyy-MM-dd HH:mm:ss", aEntity.getUpdTime()));
        }
        audit.setTableName(aEntity.getTableName());
        if (aEntity.getId() != null) {
            audit.setBeOptedObjectId(aEntity.getId().toString());
        }
        audit.setChangedContent(aEntity.getChangedContent());
        if (StringUtils.isEmpty(aEntity.getChangedContent())) {
            audit.setChangedContent(aEntity.getActionName() + aEntity.getObjectName());
        }
        if (StringUtils.isEmpty(aEntity.getTableName())) {
            audit.setTableName(aEntity.getClass().getName());
        }
        if (StringUtils.isEmpty(audit.getUpdId())) {
            audit.setCreaterId(CommonEntityUtils.getUpdId());
            audit.setUpdId(CommonEntityUtils.getUpdId());
        }
        if (StringUtils.isEmpty(audit.getUpdTime())) {
            audit.setCreateTime(new Date());
            audit.setUpdTime(CommonEntityUtils.getSystime());
        }
        if (audit.getBeOptedObjectId() != null && StringUtils.isNotEmpty(audit.getObjectName())) {
            this.specialInsert(audit);
        }
    }
}
