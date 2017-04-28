// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.logic.PwdHistoryLogic;
import com.youthen.master.logic.SystemConfigLogic;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.dao.PwdHistoryDao;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.PwdHistory;
import com.youthen.master.service.dto.SystemConfigDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("pwdHistoryLogic")
public class PwdHistoryLogicImpl implements PwdHistoryLogic {

    @Resource
    private PwdHistoryDao pwdHistoryDao;

    @Resource
    private LoginUserDao loginUserDao;

    @Resource
    private SystemConfigLogic configLogic;

    @Override
    public void save(final String userId) {
        final LoginUser user = this.loginUserDao.getById(userId);

        final Date date = java.util.Calendar.getInstance().getTime();
        final PwdHistory pwdHistory = new PwdHistory();

        pwdHistory.setUserId(user.getUserId());
        pwdHistory.setPassword(user.getPassword());
        pwdHistory.setCreateDate(date);
        try {
            this.pwdHistoryDao.insert(pwdHistory);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validatePwdChangeDay(final String aUserName, Integer earlyDays) throws ObjectNotFoundException {
        if (earlyDays == null) {
            earlyDays = 0;
        }
        final LoginUser user = this.loginUserDao.getUserByUserId(aUserName);
        final List<PwdHistory> pwdHistoryList = this.pwdHistoryDao.getAllPwdHistorysByUserId(user.getUserId());
        // 密码有效期 changeDay
        final SystemConfigDto configDto = this.configLogic.load();
        final int changeDay = configDto.getPwdChangeDays();
        if (pwdHistoryList.size() > 0) {
            final Date now = java.util.Calendar.getInstance().getTime();
            final Date end = pwdHistoryList.get(0).getCreateDate();
            final int interval = CommonUtils.getDayBetween1(now, end);
            if ((interval + earlyDays) >= changeDay) {
                return true;
            }
        }

        return false;
    }
}
