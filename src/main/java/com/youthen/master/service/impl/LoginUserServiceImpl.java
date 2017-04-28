package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.logic.EntityLogic;
import com.youthen.framework.persistence.dao.SQLDao;
import com.youthen.framework.service.impl.EntityServiceImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.logic.LoginUserLogic;
import com.youthen.master.logic.PwdHistoryLogic;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.dao.OsAudittrailDao;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LockNoticeEmail;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.persistence.entity.UserRole;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.SystemConfigService;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.LoginUserDto;
import com.youthen.master.service.dto.OsAudittrailDto;
import com.youthen.master.service.dto.SystemConfigDto;

@Service(value = "loginUserService")
@Transactional(rollbackFor = Throwable.class)
public class LoginUserServiceImpl extends EntityServiceImpl<LoginUserDto, LoginUser> implements LoginUserService {

    @Resource
    private LoginUserLogic logic;

    @Resource
    private LoginUserDao userDao;

    @Resource
    private KbnDao kbnDao;

    @Resource
    PwdHistoryLogic pwdHistoryLogic;

    @SuppressWarnings("rawtypes")
    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Resource
    SystemConfigService systemConfigService;

    @Resource
    KbnService kbnService;

    @Autowired
    private RoleService roleService;

    @Resource
    private OsAudittrailDao osAudittrailDao;

    @Autowired
    private OsAudittrailService audittrailService;

    @Resource
    SQLDao sqlDao;

    @Override
    @Transactional
    public void addUser(final LoginUserDto aDto, final List<LoginUserDto> users) throws BusinessCheckException,
            DuplicateKeyException,
            ObjectNotFoundException {
        this.logic.addUser(aDto, users);
    }

    @Override
    public void deleteUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws ObjectNotFoundException,
            OptimisticLockStolenException {
        this.logic.deleteUser(aUserDto, users);
    }

    @Override
    public void updateUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws BusinessCheckException,
            DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException {
        this.logic.updateUser(aUserDto, users);
    }

    @Override
    public void profileUpdateUser(final LoginUserDto aUserDto) throws BusinessCheckException, DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException {
        this.logic.profileUpdateUser(aUserDto);
    }

    @Override
    public List<LoginUserDto> getBackUserList(final Long aCompanyId, final Long aDeptId) {
        return this.logic.getBackUser(aCompanyId, aDeptId);
    }

    @Override
    public List<LoginUserDto> getUserList(final LoginUserDto aDto) {

        return this.logic.getUserList(aDto);

    }

    @Override
    public int getUserListCount(final LoginUserDto aUserDto) {
        return this.logic.getUserListCount(aUserDto);
    }

    @Override
    public boolean isUserExisted(final String aUserId) {

        try {
            final List<LoginUserDto> list = this.logic.selectAll();
            for (final LoginUserDto user : list) {
                if (aUserId.equals(user.getUserId())) {
                    return true;
                }
            }
            return false;
        } catch (final ObjectNotFoundException e) {
            return false;
        }

    }

    @Override
    public boolean restPwd(final String aUserId) {

        return this.logic.restPwd(aUserId);

    }

    @Override
    public boolean unlock(final String aUserId) {

        return this.logic.unlock(aUserId);

    }

    @Override
    public String isRoleExisted(final String aUserId, final Long aRoleId, final Long deptId) {

        return this.logic.isRoleExisted(aUserId, aRoleId, deptId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updatePwd(final LoginUserDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException {
        this.logic.updatePwd(aDto);
        this.pwdHistoryLogic.save(SessionContext.getUser().getUserId());
        final LoginUser aUser = this.logic.getUserByUserId(SessionContext.getUser().getUserId());
        this.pwdHistoryLogic.save(aUser.getUserId());

    }

    @Override
    public LoginUser getUserByUserId(final String aUserId) {
        return this.logic.getUserByUserId(aUserId);
    }

    @Override
    public EntityLogic<LoginUserDto, LoginUser> getLogicImpl() {
        return this.logic;
    }

    @Override
    public String validatePwd(final LoginUserDto aDto) {
        return this.logic.validatePwd(aDto);
    }

    /**
     * @see com.youthen.master.service.LoginUserService#isExceccedMaxNumUser()
     */

    @Override
    public boolean isExceccedMaxNumUser() {
        return this.userDao.isExceccedMaxNumUser();
    }

    /**
     * @throws ObjectNotFoundException
     * @see com.youthen.master.service.LoginUserService#pwdIsValid(java.lang.String)
     */

    @Override
    public boolean pwdIsValid(final String aUserId) {
        boolean isValid = false;
        try {
            isValid = this.pwdHistoryLogic.validatePwdChangeDay(aUserId, 0);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    /**
     * @see com.youthen.master.service.LoginUserService#updateErrorPwdTimes(com.youthen.master.persistence.entity.LoginUser)
     */

    @Override
    @Transactional
    public void updateErrorPwdTimes(final LoginUser aUser) {
        try {
            this.userDao.specialUpdate(aUser);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.LoginUserService#lockUser()
     */

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void lockUser(final String userId) {
        try {
            final Session curSession = this.userDao.getSessionFactory().getCurrentSession();
            // 增加密码错误次数
            final LoginUser aLoginUser = getUserByUserId(userId);

            final SystemConfigDto SystemConfigDto = this.systemConfigService.load();

            if ((aLoginUser.getPasswordErrorCount() + 1) >= SystemConfigDto.getPwdErrTime()) {

                // aLoginUser.setPasswordErrorCount(aLoginUser.getPasswordErrorCount() + 1);

                final KbnDto kbnDto = this.kbnService.getKbn("USER_STATUS", "LOCKED");

                // 更改用户为锁定状态
                curSession
                        .createSQLQuery(
                                "update t_user set status='" + kbnDto.getId() + "' where (user_id='"
                                        + aLoginUser.getUserId()
                                        + "') and status not in (select id from t_youthen_kbn where code='DELETED')")
                        .executeUpdate();

                final LockNoticeEmail email = new LockNoticeEmail();

                final LoginUserDto dto = new LoginUserDto();
                BeanUtils.copyAllNullableProperties(aLoginUser, dto);

                final OsAudittrail osAudittrail = new OsAudittrail();
                osAudittrail.setCreater(aLoginUser);// 操作者
                osAudittrail.setBeOptedObjectId(aLoginUser.getUserId());// 变更对象
                osAudittrail.setTableName(LoginUser.class.getName());
                osAudittrail.setCompanyId(aLoginUser.getCompany().getId());// 公司
                osAudittrail.setChangedContent("用户状态被修改。原值：" + aLoginUser.getStatus().getNameCn() + ";新值："
                        + kbnDto.getNameCn()); // 变更内容
                osAudittrail.setReason("密码输入错误次数超过最大限制数,被系统锁定。");// 变更原因
                osAudittrail.setObjectName(aLoginUser.getName());
                osAudittrail.setActionName("用户锁定");

                this.osAudittrailDao.insert(osAudittrail);
            }

            final String sql =
                    "update t_user set password_error_count=password_error_count+1 where   user_id='"
                            + aLoginUser.getUserId()
                            + "' and status not in (select id from t_sisqp_kbn where code='DELETED')";
            curSession.createSQLQuery(sql).executeUpdate();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.LoginUserService#haveRole(java.lang.String, java.lang.Long)
     */

    @Override
    public String haveRole(final String aUserId, final Long aRoleId) {
        return this.logic.haveRole(aUserId, aRoleId);
    }

    /**
     * @see com.youthen.master.service.LoginUserService#getUserCountBySystem(java.lang.String)
     *      计算每个系统下的人数
     */

    @SuppressWarnings("unchecked")
    @Override
    public int getUserCountBySystem(final String aString) {
        int num = 0;

        num =
                this.sqlDao
                        .count("SELECT COUNT(distinct u.USER_ID) AS CNT FROM  T_USER u,T_SISQP_KBN k WHERE u.STATUS=k.id and u.SYSTEM LIKE '%CMS%' AND k.type='USER_STATUS' and k.code<>'DELETED'");

        return num;
    }

    /**
     * @see com.youthen.master.service.LoginUserService#editUser(com.youthen.master.service.dto.LoginUserDto)
     */

    @Override
    @Transactional
    public void editUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) {
        final LoginUser user = this.userDao.getById(aUserDto.getUserId());
        final Session curSession = this.userDao.getSessionFactory().getCurrentSession();
        try {
            if ("LOCKED".equals(user.getStatus().getCode())) {// 密码错误次数清零
                final String sql =
                        "update t_user set password_error_count=0 where  user_id='" + aUserDto.getUserId() + "' ";
                curSession.createSQLQuery(sql).executeUpdate();

            }

            updateUser(aUserDto, users);

            if (users != null) {
                for (int i = 0; i < users.size(); i++) {
                    final LoginUserDto tmp = users.get(i);
                    final String child = aUserDto.getUserId();
                    // 查找
                    LoginUser existUser = null;
                    try {
                        existUser = getUserByUserId(child);
                    } catch (final Exception e) {

                    }
                    if (existUser != null) {
                        final LoginUserDto dto = new LoginUserDto();
                        if ("LOCKED".equals(existUser.getStatus().getCode())) {
                            dto.setPasswordErrorCount(0L);
                        }
                        BeanUtils.copyNullableProperties(aUserDto, dto);
                        dto.setUserId(existUser.getUserId());
                        dto.setCompanyId(tmp.getCompanyId());
                        dto.setDepartmentId(tmp.getDepartmentId());
                        dto.setRoleIds(tmp.getRoleIds());
                        dto.setSystemId(tmp.getSystemId());
                        dto.setPassword(user.getPassword());
                        updateUser(dto, users);

                    } else if (tmp.getCompanyId() != null && tmp.getDepartmentId() != null) {
                        final LoginUserDto newuser = new LoginUserDto();

                        BeanUtils.copyNullableProperties(aUserDto, newuser);
                        newuser.setUserId(aUserDto.getUserId());
                        newuser.setCompanyId(tmp.getCompanyId());
                        newuser.setDepartmentId(tmp.getDepartmentId());
                        newuser.setRoleIds(tmp.getRoleIds());
                        newuser.setSystemId(tmp.getSystemId());
                        newuser.setPassword(user.getPassword());
                        newuser.setTableName(user.getPassword());

                        this.addUser(newuser, users);
                    }

                }
            }

            // 审查追踪
            final LoginUser loginUser = this.getUserByUserId(SessionContext.getUser().getUserId());
            final OsAudittrailDto auditDto = new OsAudittrailDto();
            auditDto.setBeOptedObjectId(aUserDto.getUserId());
            auditDto.setObjectName(aUserDto.getName());
            auditDto.setCreaterId(loginUser.getUserId());
            auditDto.setReason(aUserDto.getReason());
            auditDto.setActionName("修改用户");
            auditDto.setChangedContent(aUserDto.getChangedContent());
            auditDto.setTableName(LoginUser.class.getName());
            this.audittrailService.add(auditDto);

        } catch (final BusinessCheckException e) {
            e.printStackTrace();
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (final OptimisticLockStolenException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.LoginUserService#delUser()
     */

    @Override
    @Transactional
    public void delUser(final LoginUserDto dto, final List<LoginUserDto> users) {

        String status = "";
        final String reson = dto.getReason() == null ? "" : dto.getReason();
        try {
            final LoginUser entity = this.userDao.getById(dto.getUserId());
            status = entity.getStatus().getNameCn();
            final KbnDto aKbn = this.kbnService.getKbn("USER_STATUS", "DELETED");
            final Kbn kbn = new Kbn();
            BeanUtils.copyNullableProperties(aKbn, kbn);
            entity.setStatus(kbn);

            // 审查追踪
            final LoginUser loginUser = getUserByUserId(SessionContext.getUser().getUserId());
            final OsAudittrailDto auditDto = new OsAudittrailDto();
            auditDto.setCreaterId(loginUser.getUserId());
            auditDto.setReason(reson);
            auditDto.setObjectName(dto.getName());
            auditDto.setBeOptedObjectId(dto.getUserId());
            auditDto.setActionName("修改用户状态");
            auditDto.setChangedContent("[项目名称：用户状态  原值：" + status + " 新值:失效]");
            this.audittrailService.add(auditDto);

        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.LoginUserService#getById(java.lang.String)
     */

    @Override
    public LoginUserDto getById(final String aUserId) {
        final LoginUserDto dto = new LoginUserDto();
        final LoginUser entity = this.userDao.getById(aUserId);
        BeanUtils.copyAllNullableProperties(entity, dto);

        this.masterDataMantanceService.setType(UserRole.class);
        final List<UserRole> userRoleList = this.masterDataMantanceService.queryByCondition("userId='" + aUserId + "'");

        dto.setUserRoleList(userRoleList);
        return dto;
    }

    @Override
    public List<LoginUser> getByName(final String aUserName) {
        final String hql = "from LoginUser where name='" + aUserName + "'";

        final List<LoginUser> userList = this.userDao.getByHql(hql);

        return userList;
    }

    /**
     * @see com.youthen.master.service.LoginUserService#getUserByMobileNum(java.lang.String)
     */

    @Override
    public LoginUserDto getUserByMobileNum(final String aMobileNum) {
        LoginUserDto dto = null;
        final LoginUser entity = this.userDao.getById(aMobileNum);
        if (entity != null) {
            dto = new LoginUserDto();
            BeanUtils.copyAllNullableProperties(entity, dto);
        }
        return dto;
    }

    /**
     * @see com.youthen.master.service.LoginUserService#getUserListNotPaging(com.youthen.master.service.dto.LoginUserDto)
     */

    @Override
    public List<LoginUserDto> getUserListNotPaging(final LoginUserDto aUserDto) {

        String hql = "from LoginUser where 1=1";
        if (aUserDto.getUserId() != null) {
            hql += " and userId='" + aUserDto.getUserId() + "'";
        }
        final List<LoginUser> list = this.userDao.getByHql(hql);
        final ArrayList<LoginUserDto> result = new ArrayList<LoginUserDto>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (final LoginUser entity : list) {
                final LoginUserDto dto = new LoginUserDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * @see com.youthen.master.service.LoginUserService#getUserByToken(java.lang.String)
     */

    @Override
    public LoginUserDto getUserByToken(final String aToken) {
        final LoginUserDto userDto = new LoginUserDto();
        userDto.setOpenId(aToken);
        final List<LoginUserDto> list = this.userDao.getUserList(userDto);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
