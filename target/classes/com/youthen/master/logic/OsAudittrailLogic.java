// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.service.dto.OsAudittrailDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface OsAudittrailLogic extends EntityLogic<OsAudittrailDto, OsAudittrail> {

    void add(final OsAudittrailDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

    List<OsAudittrailDto> getOsAudittrailList(OsAudittrailDto aDto);

    int getOsAudittrailCount(OsAudittrailDto aDto);

    List<OsAudittrailDto> getAllOsAudittrailList(OsAudittrailDto aDto);

}
