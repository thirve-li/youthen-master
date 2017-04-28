package com.youthen.master.service;

import java.util.List;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.service.EntityService;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.dto.LoginUserDto;

public interface LoginUserService extends EntityService<LoginUserDto> {

    LoginUserDto getUserByMobileNum(String mobileNum);

    LoginUserDto getUserByToken(String token);

    LoginUserDto getById(String userId) throws ObjectNotFoundException;

    List<LoginUser> getByName(String userName) throws ObjectNotFoundException;

    boolean isExceccedMaxNumUser();

    void addUser(LoginUserDto aDto, List<LoginUserDto> users) throws BusinessCheckException, DuplicateKeyException,
            ObjectNotFoundException;

    void deleteUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws ObjectNotFoundException,
            OptimisticLockStolenException;

    void updateUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws BusinessCheckException,
            DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException;

    void profileUpdateUser(final LoginUserDto aUserDto) throws BusinessCheckException, DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException;

    List<LoginUserDto> getBackUserList(Long companyId, final Long aDeptId) throws ObjectNotFoundException;

    public List<LoginUserDto> getUserList(final LoginUserDto userDto);

    public int getUserListCount(final LoginUserDto userDto);

    boolean isUserExisted(String aUserId);

    boolean restPwd(String aUserId);

    boolean unlock(String aUserId);

    String isRoleExisted(String aUserId, Long aRoleId, Long deptId);

    String haveRole(String aUserId, Long aRoleId);

    void updatePwd(LoginUserDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException;

    LoginUser getUserByUserId(String aUserId);

    String validatePwd(LoginUserDto aDto);

    boolean pwdIsValid(String userId);

    void updateErrorPwdTimes(LoginUser user);

    void lockUser(String userId);

    int getUserCountBySystem(String aString);

    void editUser(final LoginUserDto userDto, List<LoginUserDto> users);

    void delUser(LoginUserDto userDto, final List<LoginUserDto> users);

    List<LoginUserDto> getUserListNotPaging(final LoginUserDto userDto);

}
