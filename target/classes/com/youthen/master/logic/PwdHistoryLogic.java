// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic;

import com.youthen.framework.common.exception.ObjectNotFoundException;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface PwdHistoryLogic {

    void save(String usetId);

    boolean validatePwdChangeDay(final String aUsername, Integer earlyDays) throws ObjectNotFoundException;

}
