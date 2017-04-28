// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.persistence.entity.QueryAudittrail;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("queryAudittrailDao")
public class QueryAudittrailDao extends EntityDaoImpl<QueryAudittrail> {

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(QueryAudittrail.class);
    }

}
