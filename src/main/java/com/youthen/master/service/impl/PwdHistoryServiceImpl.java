// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.master.logic.PwdHistoryLogic;
import com.youthen.master.service.PwdHistoryService;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "pwdHistoryService")
@Transactional(rollbackFor = Throwable.class)
public class PwdHistoryServiceImpl implements PwdHistoryService {

    @Resource
    private PwdHistoryLogic pwdHistoryLogic;

    @Override
    public void save(final String userId) {
        this.pwdHistoryLogic.save(userId);

    }

    @Override
    public boolean validatePwdChangeDay(final String aUsername) throws ObjectNotFoundException {
        return this.pwdHistoryLogic.validatePwdChangeDay(aUsername, 0);
    }
}
