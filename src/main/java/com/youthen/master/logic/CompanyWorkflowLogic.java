// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.service.dto.CompanyWorkflowDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface CompanyWorkflowLogic {

    CompanyWorkflowDto findById(Long aId) throws ObjectNotFoundException;

    List<CompanyWorkflowDto> getAllCompanyWorkflow() throws ObjectNotFoundException;

    void add(final CompanyWorkflowDto aDto) throws DuplicateKeyException, ObjectNotFoundException;

    void update(CompanyWorkflowDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException;

    void delete(Long aId) throws ObjectNotFoundException, OptimisticLockStolenException;

}
