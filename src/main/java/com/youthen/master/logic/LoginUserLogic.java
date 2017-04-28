package com.youthen.master.logic;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.dto.LoginUserDto;

public interface LoginUserLogic extends EntityLogic<LoginUserDto, LoginUser> {

    void addUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws DuplicateKeyException;

    void deleteUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws ObjectNotFoundException,
            OptimisticLockStolenException;

    void updateUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws BusinessCheckException,
            DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException;

    void profileUpdateUser(final LoginUserDto aUserDto) throws BusinessCheckException, DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException;

    List<LoginUserDto> getUserByCompanyId(Long companyId);

    List<LoginUserDto> getBackUser(Long companyId, final Long aDeptId);

    List<LoginUserDto> getUserByDeptId(Long deptmentId);

    public List<LoginUserDto> getUserList(final LoginUserDto userDto);

    public int getUserListCount(final LoginUserDto userDto);

    String isRoleExisted(String aUserId, Long aRoleId, Long deptId);

    String haveRole(String aUserId, Long aRoleId);

    boolean restPwd(String aUserId);

    boolean unlock(String aUserId);

    void updatePwd(LoginUserDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException;

    LoginUser getUserByUserId(String aUserId);

    String validatePwd(LoginUserDto aDto);

}
