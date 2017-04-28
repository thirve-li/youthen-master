package com.youthen.master.logic.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.logic.impl.EntityLogicImpl;
import com.youthen.framework.persistence.dao.SQLDao;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.framework.util.CommonEntityUtils;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.logic.LoginUserLogic;
import com.youthen.master.logic.RoleLogic;
import com.youthen.master.logic.SystemConfigLogic;
import com.youthen.master.persistence.dao.CompanyDao;
import com.youthen.master.persistence.dao.DepartmentDao;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.dao.OsAudittrailDao;
import com.youthen.master.persistence.dao.PwdHistoryDao;
import com.youthen.master.persistence.dao.RoleDao;
import com.youthen.master.persistence.dao.UserRoleDao;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.OsAudittrail;
import com.youthen.master.persistence.entity.Password;
import com.youthen.master.persistence.entity.PwdHistory;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.LoginUserDto;
import com.youthen.master.service.dto.OsAudittrailDto;
import com.youthen.master.service.dto.SystemConfigDto;
import com.youthen.master.util.PasswordValidate;

@BusinessLogic("loginUserLogic")
public class LoginUserLogicImpl extends EntityLogicImpl<LoginUserDto, LoginUser> implements LoginUserLogic {

    @Resource
    private OsAudittrailDao osAudittrailDao;

    @Resource
    private LoginUserDao loginUserDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private CompanyDao companyDao;

    @Resource
    private KbnDao kbnDao;

    @Resource
    UserRoleDao userRoleDao;

    @Resource
    SQLDao sqlDao;

    @Resource
    private SystemConfigLogic configLogic;

    @Resource
    private RoleLogic roleLogic;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private PwdHistoryDao pwdHistoryDao;

    @SuppressWarnings("rawtypes")
    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Autowired
    private OsAudittrailService audittrailService;

    private String passwordRegex;

    @Override
    @Transactional
    public void addUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws DuplicateKeyException {

        initRelatedInfo(aUserDto, users);

        /*
         * try {
         * // 审查追踪
         * final LoginUser loginUser = this.loginUserDao.getUserByUserId(SessionContext.getUser().getUserId());
         * final OsAudittrailDto auditDto = new OsAudittrailDto();
         * auditDto.setBeOptedObjectId(aUserDto.getUserId());
         * auditDto.setObjectName(aUserDto.getName());
         * auditDto.setCreaterId(loginUser.getUserId());
         * auditDto.setReason(aUserDto.getReason());
         * auditDto.setActionName("新增用户");
         * auditDto.setChangedContent("新增用户" + aUserDto.getName());
         * this.audittrailService.add(auditDto);
         * } catch (final DuplicateKeyException e) {
         * e.printStackTrace();
         * } catch (final ObjectNotFoundException e) {
         * e.printStackTrace();
         * }
         */

    }

    @Override
    @Transactional
    public void deleteUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws ObjectNotFoundException,
            OptimisticLockStolenException {
        final LoginUser aUser = new LoginUser();
        BeanUtils.copyProperties(aUserDto, aUser);
        this.loginUserDao.delete(aUser);
    }

    @Override
    @Transactional
    public void updateUser(final LoginUserDto aUserDto, final List<LoginUserDto> users) throws BusinessCheckException,
            DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException {

        final LoginUser aUser = this.loginUserDao.getById(aUserDto.getUserId());

        if (aUserDto.getStatus() != null) {// 重置密码
            final Kbn kbn = this.kbnDao.getById(aUserDto.getStatus().getId());
            if (kbn != null && StringUtils.isNotEmpty(kbn.getCode()) && kbn.getCode().equalsIgnoreCase("RESET")) {
                final String pwd = this.passwordEncoder.encodePassword("f", null);
                aUserDto.setPassword(pwd);
                aUserDto.setPasswordErrorCount(0L);
            }
        }

        BeanUtils.copyNullableProperties(aUserDto, aUser);
        initRelatedInfo(aUserDto, users);
        CommonEntityUtils.initializeCommonFields(aUser);
        aUser.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
        this.loginUserDao.update(aUser);
    }

    // 用户面板处的修改
    @Override
    @Transactional
    public void profileUpdateUser(final LoginUserDto aUserDto) throws BusinessCheckException, DuplicateKeyException,
            ObjectNotFoundException, OptimisticLockStolenException {

        final LoginUser user = this.loginUserDao.getById(aUserDto.getUserId());
        BeanUtils.copyAllNullableProperties(aUserDto, user);
        this.loginUserDao.update(user);

        // 增加审查追踪
        final OsAudittrailDto auditDto = new OsAudittrailDto();
        auditDto.setCreaterId(user.getUserId());
        auditDto.setReason(aUserDto.getReason());
        auditDto.setObjectName(user.getName());
        auditDto.setActionName("修改用户信息");
        auditDto.setChangedContent(aUserDto.getChangedContent());
        this.audittrailService.add(auditDto);
    }

    /**
     * @see com.youthen.framework.logic.EntityLogic#getDtoInstance()
     */

    @Override
    public LoginUserDto getDtoInstance() {
        return new LoginUserDto();
    }

    /**
     * @see com.youthen.framework.logic.impl.EntityLogicImpl#getDaoImpl()
     */

    @Override
    public EntityDaoImpl<LoginUser> getDaoImpl() {
        return this.loginUserDao;
    }

    /**
     * @see com.youthen.master.logic.LoginUserLogic#getUserByCompanyId(java.lang.Long)
     */

    @Override
    public List<LoginUserDto> getUserByCompanyId(final Long aCompanyId) {
        final List<LoginUser> lst = this.loginUserDao.selectUserByCompanyId(aCompanyId);
        return converToDtoList(lst);
    }

    /**
     * @see com.youthen.master.logic.LoginUserLogic#getUserByDeptId(java.lang.Long)
     */

    @Override
    public List<LoginUserDto> getUserByDeptId(final Long aDeptmentId) {
        final List<LoginUser> lst = this.loginUserDao.selectUserByDepartmentId(aDeptmentId);
        return converToDtoList(lst);
    }

    /**
     * @see com.youthen.master.logic.LoginUserLogic#getBackUser(java.lang.Long)
     */

    @Override
    public List<LoginUserDto> getBackUser(final Long aCompanyId, final Long aDeptId) {

        final List<LoginUser> lst = this.loginUserDao.selectBackUser(aCompanyId, aDeptId);
        return converToDtoList(lst);
    }

    private void initRelatedInfo(final LoginUserDto aUserDto, final List<LoginUserDto> users) {

        // 设置权限
        try {
            if (users != null && users.size() > 0) {

                this.sqlDao.excuteBySql("delete from T_USER_DEPT_RLT where USER_ID='" + aUserDto.getUserId()
                        + "'");
                this.sqlDao.excuteBySql("delete from T_USER_ROLE where USER_ID = '" + aUserDto.getUserId()
                        + "'");
                this.sqlDao.excuteBySql("delete from T_USER_COMPANY_RLT where USER_ID='" + aUserDto.getUserId()
                        + "'");
                this.sqlDao.excuteBySql("delete from T_USER_SYSTEM_RLT where USER_ID='" + aUserDto.getUserId()
                        + "'");

                for (final LoginUserDto user : users) {

                    // 设置公司
                    if (user.getCompanyId() != null) {
                        this.sqlDao.excuteBySql("insert into T_USER_COMPANY_RLT(USER_ID,COMPANY_ID)  values ('"
                                + aUserDto.getUserId()
                                + "'," + user.getCompanyId() + ")");

                    }

                    // 设置部门
                    if (user.getDepartmentId() != null) {
                        this.sqlDao.excuteBySql("insert into T_USER_DEPT_RLT(USER_ID,DEPT_ID)  values ('"
                                + aUserDto.getUserId()
                                + "'," + user.getDepartmentId() + ")");
                    }

                    // 设置系统
                    for (int i = 0; i < user.getSystemIds().length; i++) {
                        this.sqlDao
                                .excuteBySql("insert into T_USER_SYSTEM_RLT(USER_ID,SYSTEM_ID)  values ('"
                                        + aUserDto.getUserId()
                                        + "',"
                                        + user.getSystemIds()[i] + ")");
                    }

                    // 设置权限
                    for (int i = 0; i < user.getRoleIds().length; i++) {

                        final Role role = this.roleDao.getById(user.getRoleIds()[i]);

                        this.sqlDao
                                .excuteBySql("insert into T_USER_ROLE(USER_ID,ROLE_ID,COMPANY_ID,DEPT_ID,SYS_ID)  values ('"
                                        + aUserDto.getUserId()
                                        + "',"
                                        + user.getRoleIds()[i]
                                        + ","
                                        + user.getCompanyId()
                                        + ","
                                        + user.getDepartmentId() + "," + role.getSystemId() + ")");
                    }

                }

                LoginUser loginUser = this.loginUserDao.getById(aUserDto.getUserId());

                if (users.get(0) != null) {
                    aUserDto.setDepartmentId(users.get(0).getDepartmentId());
                    aUserDto.setCompanyId(users.get(0).getCompanyId());
                    aUserDto.setSystemId(users.get(0).getSystemId());

                    final Company company = this.companyDao.getById(aUserDto.getCompanyId());
                    aUserDto.setCompanyCode(company.getCode());

                }

                final Role roleId = this.roleLogic.getRoleByCode("ROLE_USER");
                this.sqlDao.excuteBySql("insert into T_USER_ROLE(USER_ID,ROLE_ID,COMPANY_ID,DEPT_ID,SYS_ID)  values ('"
                        + aUserDto.getUserId() + "'," + roleId.getId() + "," + aUserDto.getCompanyId() + ","
                        + aUserDto.getDepartmentId() + ",0" + ")");

                if (loginUser == null) {
                    loginUser = new LoginUser();
                    BeanUtils.copyAllNullableProperties(aUserDto, loginUser);

                    // 密码加密
                    final String pwd = "f";
                    if (StringUtils.isEmpty(loginUser.getPassword())) {
                        loginUser.setPassword(this.passwordEncoder.encodePassword(pwd, null));
                    }
                    // new user
                    loginUser.setStatus(this.kbnDao.getKbn("USER_STATUS", "NEW_USER"));
                    loginUser.setCreateTime(new Date());
                    this.loginUserDao.insert(loginUser);
                } else {
                    BeanUtils.copyAllNullableProperties(aUserDto, loginUser);
                    this.loginUserDao.update(loginUser);
                }

            }

        } catch (final Exception e1) {

        }
    }

    @Override
    public List<LoginUserDto> getUserList(final LoginUserDto aUserDto) {
        return this.loginUserDao.getUserList(aUserDto);
    }

    @Override
    public int getUserListCount(final LoginUserDto aUserDto) {
        return this.loginUserDao.getUserListCount(aUserDto);
    }

    /**
     * 修改用户时加角色
     */
    @Override
    public String isRoleExisted(final String aUserId, final Long aRoleId, final Long deptId) {
        try {
            final List<LoginUser> lst = this.loginUserDao.getAvailableUser();

            final LoginUser srcUser = this.loginUserDao.getUserByUserId(aUserId);
            final Role srcRole = this.roleDao.getById(aRoleId);

            final Kbn userCountsFlag = this.kbnDao.getById(srcRole.getUsersCountsFlag());

            for (final LoginUser user : lst) { // 数据库中的用户
                for (final Role role : user.getRoles()) { // 数据库中的用户的角色

                    if (aRoleId == role.getId() && !aUserId.equalsIgnoreCase(user.getUserId())) {// 角色相同且非同一个人

                        if (userCountsFlag.getCode().equalsIgnoreCase("SYS_ONLY_ONE")) {// 系统中只能有一个人
                            final FieldSupportedMessage errorMessage = new FieldSupportedMessage("MST0020");
                            final String msg = errorMessage.getMesg(); //
                            return msg;
                        }

                        if (userCountsFlag.getCode().equalsIgnoreCase("COMP_ONLY_ONE")) {// 公司中仅能有一个人

                            if (user.getCompany().getId() == srcUser.getCompany().getId()) {
                                final FieldSupportedMessage errorMessage =
                                        new FieldSupportedMessage("MST0021");
                                final String msg = errorMessage.getMesg();
                                return msg;
                            }

                        }

                        if (userCountsFlag.getCode().equalsIgnoreCase("DEPT_ONLY_ONE")) {// 部门中仅能有一个人

                            if (user.getDepartment().getId() == srcUser.getDepartment().getId()) {
                                final FieldSupportedMessage errorMessage =
                                        new FieldSupportedMessage("MST0022");
                                final String msg = errorMessage.getMesg(); // 部门中仅能有一个人
                                return msg;
                            }

                        }
                    }
                }
            }

        } catch (final ObjectNotFoundException e) {
            return null;
        }

        return null;
    }

    // 新用户修改密码
    @SuppressWarnings("unchecked")
    @Override
    public void updatePwd(final LoginUserDto aDto) throws ObjectNotFoundException, OptimisticLockStolenException,
            DuplicateKeyException {
        final LoginUser aUser = this.loginUserDao.getById(SessionContext.getUser().getUserId());

        // initRelatedInfo(aDto, aUser);

        final Kbn normalStatus = this.kbnDao.getKbn("USER_STATUS", "NORMAL");
        final String pwd = aDto.getPassword();

        final LoginUser pUser = this.loginUserDao.getById(aUser.getUserId());
        pUser.setPassword(this.passwordEncoder.encodePassword(pwd, null));
        pUser.setStatus(normalStatus);
        CommonEntityUtils.initializeCommonFields(aUser);
        pUser.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
        this.loginUserDao.update(pUser);

        // 密码加密
        aUser.setPassword(this.passwordEncoder.encodePassword(pwd, null));
        aUser.setStatus(normalStatus);
        CommonEntityUtils.initializeCommonFields(aUser);
        aUser.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
        this.loginUserDao.update(aUser);

        // final Kbn userTrac = this.kbnDao.getKbn(this.TRAC_TYPE, this.USER_TRAC);
        // 增加审查追踪
        final OsAudittrail osAudittrail = new OsAudittrail();
        osAudittrail.setBeOptedObjectId(aUser.getUserId());// 变更对象
        // osAudittrail.setObjectType(userTrac.getId());// 用户
        osAudittrail.setCompanyId(aUser.getCompany().getId());// 公司
        osAudittrail.setChangedContent("修改密码");// 变更内容
        osAudittrail.setReason("新用户登录前修改密码");// 变更原因
        osAudittrail.setObjectName("用户面板");
        osAudittrail.setActionName("用户修改密码");
        osAudittrail.setTableName(LoginUser.class.getName());
        osAudittrail.setCreater(aUser);
        try {
            this.osAudittrailDao.insert(osAudittrail);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }

    }

    @Override
    public LoginUser getUserByUserId(final String aUserId) {
        return this.loginUserDao.getUserByUserId(aUserId);
    }

    @Override
    public String validatePwd(final LoginUserDto aDto) {
        String sbr = null;
        SystemConfigDto configDto = new SystemConfigDto();
        try {
            configDto = this.configLogic.load();
            // 密码强度数据获得
            final Password password = PasswordValidate.parseXml(configDto.getPwdValidtion());
            this.passwordRegex = PasswordValidate.createPasswordRegex(configDto.getPwdValidtion());
            // final Invalid when Regex
            if (!this.validateRegex(aDto, this.passwordRegex)) {
                final FieldSupportedMessage errorMessage = new FieldSupportedMessage("ACT0003");
                String msg = "";
                for (int p = 0; p < password.getStyle().size(); p++) {
                    if (password.getStyle().get(p).getIsRequired()) {

                        if (password.getStyle().get(p).getType().equals("config.password.num")) {
                            msg += " 数字";
                        }

                        if (password.getStyle().get(p).getType().equals("config.password.lower")) {
                            msg += " 小写字母";
                        }

                        if (password.getStyle().get(p).getType().equals("config.password.upper")) {
                            msg += " 大写字母";
                        }

                        if (password.getStyle().get(p).getType().equals("config.password.special")) {
                            msg += " 特殊字符";
                        }
                    }
                }

                final Object[] parms = {password.getMinLen(), password.getMaxLen(), msg};
                errorMessage.format(parms);// 替换参数值
                sbr = errorMessage.getMesg(); // 取得编号为CMN0002的properties中的信息
                return sbr;
            }
            // 判断该用户之前是否更新过
            final List<PwdHistory> pwdHistoryList = this.pwdHistoryDao.getAllPwdHistorysByUserId(aDto.getUserId());
            if (pwdHistoryList.size() > 0) {

                // Invalid when No Same
                final int times = configDto.getPwdNoSame();
                if (!this.validateNoSame(aDto, pwdHistoryList, times)) {
                    // ConfigFileHolder.setFilePath("limsMessages.properties");
                    // sbr = ConfigFileHolder.getString("Msg114");
                    // sbr = sbr.replace("{0}", times + "");

                    final FieldSupportedMessage errorMessage = new FieldSupportedMessage("ACT0004");
                    final Object[] parms = {times};
                    errorMessage.format(parms);// 替换参数值
                    sbr = errorMessage.getMesg(); // 取得编号为CMN0002的properties中的信息

                    return sbr;
                }

                // Invalid when Change Min
                final int changeMin = configDto.getPwdChangeMin();
                Date endDate = null;
                // if (!"NEW_USER".equals(aDto.getStatus().getNameEn())) {
                if (CollectionUtils.isNotEmpty(pwdHistoryList)) {
                    endDate = pwdHistoryList.get(0).getCreateDate();
                    if (this.validatePwdChangeDay(endDate, changeMin)) {
                        // ConfigFileHolder.setFilePath("limsMessages.properties");
                        // sbr = ConfigFileHolder.getString("Msg115");
                        // sbr = sbr.replace("{0}", changeMin + "");

                        final FieldSupportedMessage errorMessage = new FieldSupportedMessage("ACT0005");
                        final Object[] parms = {changeMin};
                        errorMessage.format(parms);// 替换参数值
                        sbr = errorMessage.getMesg(); // 取得编号为CMN0002的properties中的信息

                        return sbr;
                    }
                }
                // }

            }

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

        return sbr;
    }

    public boolean validateRegex(final LoginUserDto aDto, final String passwordRegexs) {
        final char charaz[] =
        {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'};
        final char charAZ[] =
        {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'};
        final char char09[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        final char charOther[] =
        {'!', '-', '/', ':', '@', '[', '`', '&', '{', ']', '~', '#', '$', '%', '^', '*', '(', ')', '+', '=',
                '|', ';', '<', '>', '?', ',', '.'};
        if (StringUtils.isBlank(passwordRegexs)) {
            return true;
        }

        final Pattern pattern = Pattern.compile(passwordRegexs);
        final Matcher matcher = pattern.matcher(aDto.getPassword());
        boolean result = false;
        result = matcher.matches();
        if (result == false) {
            return result;
        }

        final char charArray[] = aDto.getPassword().toCharArray();

        boolean exit09 = true;
        if (passwordRegexs.indexOf("0-9") > -1) {
            exit09 = false;
            for (int i = 0; exit09 == false && i < charArray.length; i++) {
                for (int j = 0; j < char09.length; j++) {
                    if (charArray[i] == char09[j]) {
                        exit09 = true;
                        break;
                    }
                }
            }
        }

        boolean exitaz = true;
        if (passwordRegexs.indexOf("a-z") > -1) {
            exitaz = false;
            for (int i = 0; exitaz == false && i < charArray.length; i++) {
                for (int j = 0; j < charaz.length; j++) {
                    if (charArray[i] == charaz[j]) {
                        exitaz = true;
                        break;
                    }
                }

            }
        }

        boolean exitAZ = true;
        if (passwordRegexs.indexOf("A-Z") > -1) {
            exitAZ = false;
            for (int i = 0; exitAZ == false && i < charArray.length; i++) {
                for (int j = 0; j < charAZ.length; j++) {
                    if (charArray[i] == charAZ[j]) {
                        exitAZ = true;
                        break;
                    }
                }

            }
        }

        boolean exitOther = true;
        if (passwordRegexs.indexOf("\u0021-\u002f\u003a-\u0040[\u005a-\u0060&&[^\u005a]]\u007b-\u007e") > -1) {
            exitOther = false;
            for (int i = 0; exitOther == false && i < charArray.length; i++) {
                for (int j = 0; j < charOther.length; j++) {
                    if (charArray[i] == charOther[j]) {
                        exitOther = true;
                        break;
                    }
                }

            }
        }

        if (exit09 && exitaz && exitAZ && exitOther) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validateNoSame(final LoginUserDto aDto, final List<PwdHistory> pwdHistoryList, final int times) {

        if (CollectionUtils.isNotEmpty(pwdHistoryList)) {
            int size = 0;
            size = Math.min(times, pwdHistoryList.size());
            for (final PwdHistory pwdHistory : pwdHistoryList.subList(0, size)) {
                final String password = this.passwordEncoder.encodePassword(aDto.getPassword(), null);
                if (password.equals(pwdHistory.getPassword())) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean validatePwdChangeDay(final Date end, final int changeMin) {
        final Date now = java.util.Calendar.getInstance().getTime();
        final int interval = CommonUtils.getDayBetween1(now, end);
        if (interval < changeMin) {
            return true;
        }
        return false;
    }

    /**
     * @see com.youthen.master.logic.LoginUserLogic#restPwd(java.lang.String)
     */

    @Override
    public boolean restPwd(final String aUserId) {

        try {
            final LoginUser aUser = this.loginUserDao.getById(aUserId);
            final String pwd = "Aa12345,";

            final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "RESET");
            final String status = aUser.getStatus().getNameCn();
            aUser.setStatus(kbn);

            // final Kbn userTrac = this.kbnDao.getKbn(this.TRAC_TYPE, this.USER_TRAC);
            // 增加审查追踪
            final OsAudittrail osAudittrail = new OsAudittrail();
            osAudittrail.setCreater(aUser);// 操作者
            osAudittrail.setBeOptedObjectId(aUser.getUserId());// 变更对象
            osAudittrail.setTableName(LoginUser.class.getName());
            osAudittrail.setCompanyId(aUser.getCompany().getId());// 公司
            osAudittrail.setChangedContent(DateFormatUtils.format("yyyy-MM-dd", new Date()) + "," + aUser.getName()
                    + "被"
                    + aUser.getUpdId() + "重置密码, 用户状态被修改，原值为：" + status + "，新值为:密码被重置；"); // 变更内容

            osAudittrail.setReason("重置密码");// 变更原因
            osAudittrail.setObjectName("用户面板");
            osAudittrail.setActionName("用户重置密码");
            this.osAudittrailDao.insert(osAudittrail);

            // 密码加密
            aUser.setPassword(this.passwordEncoder.encodePassword(pwd, null));
            CommonEntityUtils.initializeCommonFields(aUser);
            aUser.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
            this.loginUserDao.update(aUser);

            // this.pwdHistoryLogic.save(aUser.getUserId());

            return true;

        } catch (final DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public boolean unlock(final String aUserId) {

        try {
            final LoginUser aUser = this.loginUserDao.getById(aUserId);
            final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "NORMAL");
            aUser.setStatus(kbn);

            // 增加审查追踪
            final OsAudittrail osAudittrail = new OsAudittrail();
            osAudittrail.setCreater(aUser);// 操作者
            osAudittrail.setBeOptedObjectId(aUser.getUserId());// 变更对象
            osAudittrail.setTableName(LoginUser.class.getName());
            osAudittrail.setCompanyId(aUser.getCompany().getId());// 公司
            osAudittrail.setChangedContent(DateFormatUtils.format("yyyy-MM-dd", new Date()) + "," + aUser.getName()
                    + "被"
                    + aUser.getUpdId() + "密码解锁, 用户状态被修改，原值为：被锁定，新值为:新用户；"); // 变更内容
            osAudittrail.setReason("密码解锁");// 变更原因
            osAudittrail.setObjectName("用户面板");
            osAudittrail.setActionName("用户密码解锁");
            this.osAudittrailDao.insert(osAudittrail);
            CommonEntityUtils.initializeCommonFields(aUser);
            aUser.setUpdTime(DateFormatUtils.format("yyyy-MM-dd", new Date()));
            this.loginUserDao.update(aUser);
            return true;

        } catch (final DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    /**
     *  修改用户时减去角色
     */
    public String haveRole(final String aUserId, final Long aRoleId) {

        try {
            final List<LoginUser> lst = this.loginUserDao.getAvailableUser();

            final LoginUser srcUser = this.loginUserDao.getUserByUserId(aUserId);
            final Role srcRole = this.roleDao.getById(aRoleId);

            final Kbn userCountsFlag = this.kbnDao.getById(srcRole.getUsersCountsFlag());

            int flag = 0;

            for (final LoginUser user : lst) { // 系统中已有用户
                if (!aUserId.equalsIgnoreCase(user.getUserId())) {// 角色相同，且非同一个人

                    if (userCountsFlag.getCode().equalsIgnoreCase("SYS_ONLY_ONE")) {// 系统中只能有一人
                        if (user.hasRole(srcRole.getCode())) {
                            flag = 1;
                        }
                    }

                    if (userCountsFlag.getCode().equalsIgnoreCase("COMP_ONLY_ONE")) {// 公司中只能有一人

                        if (user.getCompany().getId() == srcUser.getCompany().getId()) {
                            if (user.hasRole(srcRole.getCode())) {
                                flag = 2;
                            }
                        }

                    }

                    if (userCountsFlag.getCode().equalsIgnoreCase("DEPT_ONLY_ONE")) {// 此角色该部门中只能有一人

                        if (user.getDepartment().getId() == srcUser.getDepartment().getId()) {
                            if (user.hasRole(srcRole.getCode())) {
                                flag = 3;
                            }
                        }

                    }

                    if (userCountsFlag.getCode().equalsIgnoreCase("MULTIPLE")) {// 可多人
                        if (user.hasRole(srcRole.getCode())) {
                            flag = 4;
                        }
                    }

                }
            }

            if (flag == 0) {

                if (userCountsFlag.getCode().equalsIgnoreCase("SYS_ONLY_ONE")) {// 系统中只能有一人
                    final FieldSupportedMessage errorMessage =
                            new FieldSupportedMessage("MST0014");// 此角色该系统中只能有1人。
                    final String msg = errorMessage.getMesg();
                    return msg;
                }

                if (userCountsFlag.getCode().equalsIgnoreCase("COMP_ONLY_ONE")) {// 公司中只能有一人

                    final FieldSupportedMessage errorMessage =
                            new FieldSupportedMessage("MST0023");
                    final String msg = errorMessage.getMesg(); // 此角色该公司中只能有1人。

                    return msg;
                }

                if (userCountsFlag.getCode().equalsIgnoreCase("DEPT_ONLY_ONE")) {// 此角色该部门中只能有一人

                    final FieldSupportedMessage errorMessage =
                            new FieldSupportedMessage("MST0024");
                    final String msg = errorMessage.getMesg(); // 取得编号为MST0020的properties中的信息
                                                               // 此角色该公司中只能有1人。
                    return msg;
                }

                if (userCountsFlag.getCode().equalsIgnoreCase("MULTIPLE")) {// 可多人
                    final FieldSupportedMessage errorMessage =
                            new FieldSupportedMessage("MST0014");
                    final String msg = errorMessage.getMesg(); // 取得编号为MST0020的properties中的信息
                                                               // 此角色该公司中只能有1人。
                    return msg;
                }

            }
            return null;
        } catch (final Exception e) {
            return null;
        }

    }

}
