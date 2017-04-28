// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.persistence.entity.PwdHistory;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("pwdHistoryDao")
public class PwdHistoryDao extends EntityDaoImpl<PwdHistory> {

    @Override
    public void injectType() {
        this.setType(PwdHistory.class);
    }

    @SuppressWarnings("unchecked")
    public List<PwdHistory> getAllPwdHistorysByUserId(final String aUserId) {
        return this.getByHql("from PwdHistory where userId='" + aUserId + "'" + "  ORDER BY createDate DESC");
    }

}
