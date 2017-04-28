// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import com.youthen.framework.service.EntityService;
import com.youthen.master.persistence.entity.Function;
import com.youthen.master.service.dto.FunctionDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface FunctionService extends EntityService<FunctionDto> {

    Function getFunctionByCode(String aFunctioncd);

    void save(final FunctionDto funcDto);
}
