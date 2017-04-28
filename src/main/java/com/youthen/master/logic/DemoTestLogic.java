// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.master.persistence.entity.DemoTest;
import com.youthen.master.service.dto.DemoTestDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface DemoTestLogic extends EntityLogic<DemoTestDto, DemoTest> {

    void addUser(final DemoTestDto aUserDto) throws DuplicateKeyException;

}
