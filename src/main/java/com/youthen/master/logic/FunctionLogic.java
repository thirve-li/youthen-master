// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import com.youthen.framework.logic.EntityLogic;
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
public interface FunctionLogic extends EntityLogic<FunctionDto, Function> {

    Function getFunctionByCode(String aFunctioncd);

}
