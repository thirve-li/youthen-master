package com.youthen.master.presentation.action;

import com.youthen.framework.common.exception.BusinessException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.presentation.action.BaseAction;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.MasterEntryDto;

public abstract class CommonMasterMaintenanceAction<T extends MasterEntryDto> extends BaseAction {

    LoginUserService loginUserService;

    private static final long serialVersionUID = -5991642170210983527L;

    public abstract void setDto(T aDto);

    public abstract T getDto();

    protected abstract void checkVersionno(String aMstKey, T aDto) throws OptimisticLockStolenException;

    protected void setErrorMessages(final BusinessException aE) {
        CommonMessage.addError(this, "", aE);
    }

    protected void setErrorMessages(final String aPrefix, final BusinessException aE) {
        CommonMessage.addError(this, aPrefix, aE);
    }
}
