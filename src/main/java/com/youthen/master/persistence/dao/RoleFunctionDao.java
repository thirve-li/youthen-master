// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.persistence.entity.RoleFunction;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("roleFunctionDao")
public class RoleFunctionDao extends EntityDaoImpl<RoleFunction> {

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(RoleFunction.class);
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunction> getByHql(final Long param) {
        final String hql = "from RoleFunction where roleId=?";
        final List<RoleFunction> foleFunctionList = getHibernateTemplate().find(hql, param);
        return foleFunctionList;
    }
}
