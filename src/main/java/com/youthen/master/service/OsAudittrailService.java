// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.service.EntityService;
import com.youthen.master.service.dto.OsAudittrailDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface OsAudittrailService extends EntityService<OsAudittrailDto> {

    void add(OsAudittrailDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

    List<OsAudittrailDto> getOsAudittrailList(OsAudittrailDto aDto);

    int getOsAudittrailCount(OsAudittrailDto aDto);

    List<OsAudittrailDto> getAllOsAudittrailList(OsAudittrailDto aDto);

}
