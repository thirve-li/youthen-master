// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import com.youthen.framework.common.exception.ObjectNotFoundException;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface PwdHistoryService {

    void save(String userId);

    boolean validatePwdChangeDay(final String aUsername) throws ObjectNotFoundException;

}
