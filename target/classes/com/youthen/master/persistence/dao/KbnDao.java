// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.persistence.entity.Kbn;

/**
 * 。
 * 
 * @author Lixin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("kbnDao")
public class KbnDao extends EntityDaoImpl<Kbn> {

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(Kbn.class);
    }

    @SuppressWarnings("unchecked")
    public Kbn getKbn(final String type, final String code) {
        final String hql = "from Kbn where type='" + type + "' and code='" + code + "'";
        final List<Kbn> result = this.getByHql(hql);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Kbn> getKbnListByType(final String type) {
        final String hql = "from Kbn where type='" + type + "' and status=1";
        final List<Kbn> result = this.getByHql(hql);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Kbn> getKbnListBy(final Kbn kbn) {
        final StringBuffer hql = new StringBuffer("FROM " + kbn.getClass().getName() + " WHERE 1=1");
        if (kbn.getId() != null) {
            hql.append(" AND id=" + kbn.getId());
        }
        if (StringUtils.isNotEmpty(kbn.getType())) {
            hql.append(" AND type='" + kbn.getType() + "'");
        }
        if (StringUtils.isNotEmpty(kbn.getCode())) {
            hql.append(" AND code='" + kbn.getCode() + "'");
        }
        if (StringUtils.isNotEmpty(kbn.getNameEn())) {
            hql.append(" AND nameEn='" + kbn.getNameEn() + "'");
        }
        if (StringUtils.isNotEmpty(kbn.getNameCn())) {
            hql.append(" AND nameCn='" + kbn.getNameCn() + "'");
        }
        if (kbn.getParentTypeId() != null) {
            hql.append(" AND parentType.id=" + kbn.getParentTypeId());
        }
        if (kbn.getStatus() != null) {
            hql.append(" AND status=" + kbn.getStatus());
        }

        return this.getByHql(hql.toString());
    }
}
