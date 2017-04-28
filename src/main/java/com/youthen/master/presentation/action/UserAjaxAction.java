package com.youthen.master.presentation.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.ConfigFileHolder;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.fields.FieldSupportedMessage;
import com.youthen.framework.presentation.action.AbstractAjaxAction;
import com.youthen.framework.util.WeatherUtil;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.dao.RoleDao;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.dto.DepartmentDto;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.LoginUserDto;
import com.youthen.master.service.dto.RoleDto;
import com.youthen.master.util.SystemConst;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-user")
@Controller
@ExecAuthority(functioncd = "1")
public class UserAjaxAction extends AbstractAjaxAction {

    private static final long serialVersionUID = 1L;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private KbnService kbnService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DepartmentService departmentService;

    @Resource
    private KbnDao kbnDao;

    private String userId;

    private String pwd;

    private Long deptId;

    private Long roleId;

    private String roleCode;

    private Long systemId;
    private String systemName;
    private String oldPwd;

    private Long status;

    private String companyId;
    private Long sysId;
    private Long parentColumId;

    /**
     * 取得天气预报。
     * 
     * @return
     */
    public Object getWeather() {
        final WeatherUtil weather = new WeatherUtil();
        final List<String> weatherList = weather.getWeather(2013);// 2013上海
        return weatherList;
    }

    public Object getColumn() {
        final KbnDto condition = new KbnDto();
        condition.setParentTypeId(this.parentColumId);
        return this.kbnService.getKbnListBy(condition);
    }

    @SuppressWarnings("boxing")
    public Object getDeptByCompanyId() {
        final List<Department> departList = new ArrayList<Department>();
        try {
            final List<DepartmentDto> dtos = this.departmentService.getByCompanyId(Long.parseLong(this.companyId));
            for (final DepartmentDto dto : dtos) {
                final Department depart = new Department();
                depart.setId(dto.getId());
                depart.setName(dto.getName());
                departList.add(depart);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return departList;
    }

    public Object getRolesBySysId() {
        final List<Role> roleList = new ArrayList<Role>();
        try {
            final List<RoleDto> dtos = this.roleService.selectAll();
            for (final RoleDto dto : dtos) {
                if (this.sysId != null) {
                    if (dto.getSystemId() == this.sysId) {
                        final Role role = new Role();
                        role.setName(dto.getName());
                        role.setId(dto.getId());
                        role.setCode(dto.getCode());
                        roleList.add(role);
                    }
                    if (dto.getCode().startsWith("ADMIN")) {
                        final Role role = new Role();
                        role.setName(dto.getName());
                        role.setId(dto.getId());
                        role.setCode(dto.getCode());
                        roleList.add(role);
                    }
                }

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return roleList;
    }

    @Override
    protected Object doExecute(final Object aArgs) throws Exception {
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        final LoginUserDto dto = this.loginUserService.getById(this.userId);
        if (StringUtils.isEmpty(dto.getUserId())) {
            // final String sbr = ConfigFileHolder.getString("Msg039");

            final FieldSupportedMessage errorMessage = new FieldSupportedMessage("MST0003");
            // final Object[] parms = {times};
            // errorMessage.format(parms);// 替换参数值
            final String sbr = errorMessage.getMesg(); // 取得编号为CMN0002的properties中的信息

            resultMap.put("message", sbr);
            resultMap.put("result", new Boolean(false));
        } else {

            if (dto.getStatus().getCode().equalsIgnoreCase("DELETED")
                    || dto.getStatus().getCode().equalsIgnoreCase("LOCKED")) {

                if (dto.getStatus().getCode().equalsIgnoreCase("DELETED")) {
                    final FieldSupportedMessage errorMessage = new FieldSupportedMessage("EFW56004");
                    resultMap.put("message", errorMessage.getMesg());
                    resultMap.put("result", new Boolean(false));
                }

                if (dto.getStatus().getCode().equalsIgnoreCase("LOCKED")) {
                    final FieldSupportedMessage errorMessage = new FieldSupportedMessage("EFW56003");
                    resultMap.put("message", errorMessage.getMesg());
                    resultMap.put("result", new Boolean(false));
                }

            } else {
                if (!this.passwordEncoder.encodePassword(this.pwd, null).equals(dto.getPassword())) {
                    // final String sbr = ConfigFileHolder.getString("Msg040");
                    final FieldSupportedMessage errorMessage = new FieldSupportedMessage("MST0004");
                    final String sbr = errorMessage.getMesg();
                    resultMap.put("message", sbr);
                    resultMap.put("result", new Boolean(false));
                } else {
                    resultMap.put("result", new Boolean(true));
                }
            }
        }
        return resultMap;
    }

    @SuppressWarnings("boxing")
    public Object restPwd() {
        return this.loginUserService.restPwd(this.userId);

    }

    @SuppressWarnings("boxing")
    public Object unlock() {
        return this.loginUserService.unlock(this.userId);

    }

    public Object getUser() {
        try {
            return this.loginUserService.getById(this.userId);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return new LoginUserDto();
    }

    @SuppressWarnings("boxing")
    public Object isUserExisted() {
        return this.loginUserService.isUserExisted(this.userId);
    }

    public Object isRoleExisted() {
        return this.loginUserService.isRoleExisted(this.userId, this.roleId, this.deptId);
    }

    public Object haveRole() {
        return this.loginUserService.haveRole(this.userId, this.roleId);
    }

    public boolean isExceccedMaxNumUser() {
        return this.loginUserService.isExceccedMaxNumUser();
    }

    public Object isMaxNumUser() {
        String msg = null;
        if (this.systemName.contains("CMS")) {
            final int cmsNum = this.loginUserService.getUserCountBySystem("CMS");
            if (cmsNum + 1 > SystemConst.CMS_USER_NUM) {
                ConfigFileHolder.setFilePath("app_messages_zh_CN.properties");
                msg = ConfigFileHolder.getString("MST0012", new String[] {"CMS用户数"});
                return msg;
            }
        }
        if (this.systemName.contains("DMS")) {
            final int dmsNum = this.loginUserService.getUserCountBySystem("DMS");
            if (dmsNum + 1 > SystemConst.DMS_USER_NUM) {
                ConfigFileHolder.setFilePath("app_messages_zh_CN.properties");
                msg = ConfigFileHolder.getString("MST0012", new String[] {"DMS用户数"});
                return msg;
            }
        }
        if (this.systemName.contains("QMS")) {
            final int qmsNum = this.loginUserService.getUserCountBySystem("QMS");
            if (qmsNum + 1 > SystemConst.QMS_USER_NUM) {
                ConfigFileHolder.setFilePath("app_messages_zh_CN.properties");
                msg = ConfigFileHolder.getString("MST0012", new String[] {"QMS用户数"});
                return msg;
            }
        }
        return msg;
    }

    public boolean isOldPwdOk() {

        try {
            final LoginUserDto aDto = new LoginUserDto();
            aDto.setUserId(this.userId);
            final LoginUserDto user = this.loginUserService.getById(this.userId);
            if (user.getPassword().equalsIgnoreCase(this.passwordEncoder.encodePassword(this.oldPwd, null))) {
                return true;
            }
            return false;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Object validatePwd() {
        final LoginUserDto aDto = new LoginUserDto();
        aDto.setUserId(this.userId);
        aDto.setPassword(this.pwd);
        final String msg = this.loginUserService.validatePwd(aDto);
        return msg;
    }

    // 校验角色管理中角色code的唯一性
    public Object validateRoleCode() {

        String msg = null;
        try {
            final RoleDto aDto = new RoleDto();
            aDto.setCode(this.roleCode);
            aDto.setId(this.roleId);
            msg = this.roleService.validateCode(aDto);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public int setPwdErrCount() {
        final Kbn kbn = this.kbnDao.getById(this.status);
        if ("NORMAL".equals(kbn.getCode())) {
            return 0;
        }

        return -1;
    }

    public int getUserStatus() {
        final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "NORMAL");
        if (kbn != null) {
            return kbn.getId().intValue();
        }

        return -1;
    }

    public int resetStatus() {
        final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "RESET");
        if (kbn != null) {
            return kbn.getId().intValue();
        }

        return -1;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String aUserId) {
        this.userId = aUserId;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(final String aPwd) {
        this.pwd = aPwd;
    }

    /**
     * getter for deptId.
     * 
     * @return deptId
     */
    public Long getDeptId() {
        return this.deptId;
    }

    /**
     * setter for deptId.
     * 
     * @param aDeptId deptId
     */
    public void setDeptId(final Long aDeptId) {
        this.deptId = aDeptId;
    }

    /**
     * getter for roleId.
     * 
     * @return roleId
     */
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * setter for roleId.
     * 
     * @param aRoleId roleId
     */
    public void setRoleId(final Long aRoleId) {
        this.roleId = aRoleId;
    }

    /**
     * getter for roleCode.
     * 
     * @return roleCode
     */
    public String getRoleCode() {
        return this.roleCode;
    }

    /**
     * setter for roleCode.
     * 
     * @param aRoleCode roleCode
     */
    public void setRoleCode(final String aRoleCode) {
        this.roleCode = aRoleCode;
    }

    /**
     * getter for systemId.
     * 
     * @return systemId
     */
    public Long getSystemId() {
        return this.systemId;
    }

    /**
     * setter for systemId.
     * 
     * @param aSystemId systemId
     */
    public void setSystemId(final Long aSystemId) {
        this.systemId = aSystemId;
    }

    /**
     * getter for systemName.
     * 
     * @return systemName
     */
    public String getSystemName() {
        return this.systemName;
    }

    /**
     * setter for systemName.
     * 
     * @param aSystemName systemName
     */
    public void setSystemName(final String aSystemName) {
        this.systemName = aSystemName;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(final Long aStatus) {
        this.status = aStatus;
    }

    /**
     * getter for companyId.
     * 
     * @return companyId
     */
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * setter for companyId.
     * 
     * @param aCompanyId companyId
     */
    public void setCompanyId(final String aCompanyId) {
        this.companyId = aCompanyId;
    }

    /**
     * getter for sysId.
     * 
     * @return sysId
     */
    public Long getSysId() {
        return this.sysId;
    }

    /**
     * setter for sysId.
     * 
     * @param aSysId sysId
     */
    public void setSysId(final Long aSysId) {
        this.sysId = aSysId;
    }

    /**
     * getter for oldPwd.
     * 
     * @return oldPwd
     */
    public String getOldPwd() {
        return this.oldPwd;
    }

    /**
     * setter for oldPwd.
     * 
     * @param aOldPwd oldPwd
     */
    public void setOldPwd(final String aOldPwd) {
        this.oldPwd = aOldPwd;
    }

    /**
     * getter for parentColumId.
     * 
     * @return parentColumId
     */
    public Long getParentColumId() {
        return this.parentColumId;
    }

    /**
     * setter for parentColumId.
     * 
     * @param aParentColumId parentColumId
     */
    public void setParentColumId(final Long aParentColumId) {
        this.parentColumId = aParentColumId;
    }

}
