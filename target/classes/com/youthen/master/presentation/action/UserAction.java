package com.youthen.master.presentation.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.youthen.framework.common.SimpleAppMessage;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.common.BackUpUserUtil;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.persistence.entity.SubSystem;
import com.youthen.master.service.ArticleService;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.NoticeService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.dto.ArticleDto;
import com.youthen.master.service.dto.CompanyDto;
import com.youthen.master.service.dto.DepartmentDto;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.LoginUserDto;
import com.youthen.master.service.dto.NoticeDto;
import com.youthen.master.service.dto.OsAudittrailDto;
import com.youthen.master.service.dto.RoleDto;

@Namespace("/mst-user")
@Results({
        @Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "index", location = "/WEB-INF/jsp/master/index.jsp"),
        @Result(name = INPUT, location = "/WEB-INF/jsp/master/user/add.jsp"),
        @Result(name = "add", location = "/mst-user/list.action", type = "redirect"),
        @Result(name = "edit", location = "/mst-user/list.action", type = "redirect"),
        @Result(name = "editInit", location = "/WEB-INF/jsp/master/user/edit.jsp"),
        @Result(name = "viewInit", location = "/WEB-INF/jsp/master/user/view.jsp"),
        @Result(name = "changePwdInit", location = "/WEB-INF/jsp/master/user/changePwd.jsp"),
        @Result(name = "profileEditInit", location = "/WEB-INF/jsp/master/user/currentEdit.jsp"),
        @Result(name = SUCCESS, location = "/WEB-INF/jsp/master/user/list.jsp"),
        @Result(name = "backupUser", location = "/WEB-INF/jsp/master/user/backupUser.jsp"),
        @Result(name = "editBackupUser", location = "/mst-user/index.action", type = "redirect"),
        @Result(name = "logout", location = "/j_spring_security_logout", type = "redirect"),
        @Result(name = "removeBackupUser", location = "/oa-front/index.action", type = "redirect"),
        @Result(name = "toProfileEditInit", location = "/oa-front/index.action", type = "redirect")

})
@Controller
// @ExecAuthority(functioncd = "MST-ADMIN-001")
public class UserAction extends CommonMasterMaintenanceAction<LoginUserDto> {

    private static final long serialVersionUID = -4372884128344672292L;

    private LoginUserDto dto;

    private DepartmentDto deptDto;
    private CompanyDto companyDto;

    // 用户集合
    private List<LoginUserDto> userList;

    // 角色集合
    private List<RoleDto> roleList;

    // 公司集合
    private List<Company> companyList;

    // 部门集合
    private List<DepartmentDto> departmentList;

    // KBN
    private List<KbnDto> kbnList;

    // 多用户
    private List<LoginUserDto> users;

    // 公司新闻
    private List<ArticleDto> comList;

    // 八面来风
    private List<ArticleDto> eightList;

    // 公告集合
    private List<NoticeDto> noticedtoList;

    // BackUp用户集合
    private List<LoginUserDto> backUserList;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private KbnService kbnService;

    @Autowired
    private LoginUserDao userDao;

    @Autowired
    private KbnDao kbnDao;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Autowired
    private OsAudittrailService audittrailService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NoticeService noticeService;

    private String namespace;

    private Long companyId;

    private Long departmentId;

    private String userId;

    private Long roleId;

    private int tabId = 2;

    private int columnIds;

    private final int flag = 1;

    private int hasBackUpauth;

    private Role role;

    private String image;

    // 用于存储修改密码返回的错误信息
    private String msg;
    // 用于解除备用用户
    private LoginUserDto removeDto;

    private LoginUser loginUser;

    // 审查公告栏List集合
    private List<OsAudittrailDto> logList;

    private Long userStatusId;

    private int isKSHZ;

    private List<KbnDto> bigColumList;

    private List<SubSystem> systemList;

    private File img;

    private String imgContentType;

    private String imgFileName;

    /**
     * getter for tabId.
     * 
     * @return tabId
     */
    public int getTabId() {
        return this.tabId;
    }

    /**
     * setter for tabId.
     * 
     * @param aTabId tabId
     */
    public void setTabId(final int aTabId) {
        this.tabId = aTabId;
    }

    private void init() {
        try {
            final Long compId = this.dto.getCompanyId();
            final Long deptId = this.dto.getDepartmentId();
            this.departmentList = this.departmentService.getByCompanyId(compId);

            this.backUserList = this.loginUserService.getBackUserList(compId, deptId);
            final RoleDto aDto = new RoleDto();
            aDto.setPageSize(1000);
            this.roleList = this.roleService.getRoleList(aDto);

            this.masterDataMantanceService.setType(Kbn.class);
            this.kbnList =
                    this.masterDataMantanceService
                            .queryByCondition("type='USER_STATUS' and (code='DELETED' or code='HOLIDAY' or code='NORMAL' or code='LOCKED' or code='RESET' or code='NEW_USER')");

            this.masterDataMantanceService.setType(Company.class);
            this.companyList = this.masterDataMantanceService.queryByCondition("status=1");

            final Map<String, List<DepartmentDto>> comDeptMap = new HashMap<String, List<DepartmentDto>>();

            for (final Company company : this.companyList) {
                final List<DepartmentDto> deptList = this.departmentService.getByCompanyId(company.getId());
                comDeptMap.put(company.getId().toString(), deptList);
            }
            ActionContext.getContext().put("comDeptMap", comDeptMap);

            this.masterDataMantanceService.setType(SubSystem.class);
            this.systemList = this.masterDataMantanceService.selectAll();

            this.bigColumList = this.kbnService.getBigColum();

        } catch (final ObjectNotFoundException e) {
            this.setErrorMessages(e);
        }
    }

    @Action("main")
    public String main() {
        this.bigColumList = this.kbnService.getBigColum();
        this.columnIds = 2;
        this.namespace = "mst-user";
        return "main";
    }

    /*
     * 主页显示
     */
    @Action("index")
    public String index() {
        this.bigColumList = this.kbnService.getBigColum();
        this.columnIds = 1;
        return "index";
    }

    /**
     * 显示头部LOGO
     * 显示尾部LOGO
     * 
     * @return String
     */
    @Action("showImg")
    public void showImg() {

        try {
            this.companyDto = this.companyService.getById(this.companyDto.getId());
            Blob b = null;
            if (this.flag == 1) {
                b = this.companyDto.getHeaderLogo();

            } else if (this.flag == 2) {
                b = this.companyDto.getFooterLogo();

            }
            if (b != null) {
                final InputStream ins = b.getBinaryStream();
                ServletActionContext.getResponse().setContentType("image/jpeg");
                final OutputStream outs = ServletActionContext.getResponse().getOutputStream();
                final byte[] buff = new byte[1024];
                int i = 0;
                while ((i = ins.read(buff)) != -1) {
                    outs.write(buff);
                }
                ins.close();
                outs.close();
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入用户信息列表初始化页面
     * 
     * @return String
     */
    @Action("list")
    public String list() {

        this.kbnList = this.kbnService.getKbnListByType("USER_STATUS");
        this.dto.setPageSize(10);
        this.userList = this.loginUserService.getUserList(this.dto);

        try {
            this.roleList = this.roleService.selectCommon();
            if (this.dto != null && this.dto.getDepartmentId() != null) {
                this.deptDto = this.departmentService.getById(this.dto.getDepartmentId());
                this.companyDto = this.companyService.getById(this.deptDto.getCompanyId());
            }

            if (this.dto != null && this.dto.getCompanyId() != null) {
                this.companyDto = this.companyService.getById(this.dto.getCompanyId());
            }

            for (int i = 0; i < this.roleList.size(); i++) {
                final RoleDto role = this.roleList.get(i);
                if ("ROLE_USER".equals(role.getCode())) {
                    this.roleList.remove(i);
                }
            }
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

        final int listSize = this.loginUserService.getUserListCount(this.dto);
        final int pages = CommonUtils.countPages(listSize, this.dto.getPageSize());
        this.dto.setPages(pages);
        this.dto.setListSize(listSize);
        return SUCCESS;
    }

    // 页面错误信息提示
    public void message(final String mesg) {
        final SimpleAppMessage messageMsg = new SimpleAppMessage("EMS01001").format(mesg);
        final String messageStr = messageMsg.getMesg();
        this.addActionMessage(messageStr);
        ActionContext.getContext().getSession().put("ERROR_MESSAGE", messageStr);
    }

    // 后台校验角色选择（系统只能有一人，本部门只能有一人）
    @SuppressWarnings({"hiding"})
    public boolean isRoleExisted() {
        final Long[] roleIds = this.dto.getRoleIds();
        if (roleIds.length > 0) {
            for (final Long roleId : roleIds) {
                if (this.loginUserService.isRoleExisted(this.dto.getUserId(), roleId, this.departmentId) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加用户，初始化页面
     * 
     * @return String
     */
    @ExecAuthority(functioncd = "1002")
    @Action("addInit")
    public String addInit() {
        init();

        final KbnDto aKbn = this.kbnService.getKbn("USER_STATUS", "NEW_USER");
        if (aKbn != null) {
            this.userStatusId = aKbn.getId();
        }

        return INPUT;
    }

    /**
     * 新增用户信息
     * 
     * @return String
     */
    @Action("add")
    public String add() {

        try {
            this.dto.setDepartmentId(null);
            this.dto.setCompanyId(null);
            this.loginUserService.addUser(this.dto, this.users);

        } catch (final BusinessCheckException e) {
            this.setErrorMessages(e);
        } catch (final DuplicateKeyException e) {
            this.setErrorMessages(e);
        } catch (final ObjectNotFoundException e) {
            this.setErrorMessages(e);
        }

        this.dto.setName(null);
        return "add";
    }

    /**
     * 修改用户信息初始化页面
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    @Action("editInit")
    public String editInit() {
        init();

        try {
            this.roleList = this.roleService.selectAll();
            for (final RoleDto dto : this.roleList) {
                if (dto.getCode().equals("ROLE_USER")) {
                    this.roleList.remove(dto);
                    break;
                }
            }

            this.dto = this.loginUserService.getById(this.dto.getUserId());

        } catch (final Exception e) {
            e.printStackTrace();
        }

        return "editInit";
    }

    /**
     * 查看用户信息初始化页面
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    @Action("viewInit")
    public String veiwInit() {
        init();
        try {
            this.dto = this.loginUserService.getById(this.dto.getUserId());
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "viewInit";
    }

    /**
     * 保存修改用户信息
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    @Action("edit")
    public String edit() {
        this.loginUserService.editUser(this.dto, this.users);

        return "edit";
    }

    /**
     * 用户失效
     * 
     * @param aRoles
     * @return String
     */
    @SuppressWarnings("unchecked")
    @Action("delete")
    public String delete() {
        this.loginUserService.delUser(this.dto, this.users);
        return list();
    }

    @Action("profileEditInit")
    public String profileEditInit() {
        init();
        this.columnIds = 1;
        try {
            this.dto = this.loginUserService.getById(this.dto.getUserId());
            this.hasBackUpauth = BackUpUserUtil.getBackUpAuth(this.dto.getRoles()); // 获取是否有权限分配备份用户
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "profileEditInit";
    }

    @Action("profileEdit")
    public String profileEdit() {
        try {

            if (this.img != null) {
                final HttpServletRequest request = ServletActionContext.getRequest();
                final HttpSession session = request.getSession();

                final String fileName = System.currentTimeMillis()
                        + this.imgFileName.substring(this.imgFileName.lastIndexOf("."));
                session.setAttribute("fileName", fileName);

                final long fileSize = this.img.length();
                session.setAttribute("fileSize", fileSize);

                final String path =
                        ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/images");

                final File fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                final String filePath = path + File.separator + fileName;
                final File fileTmp = new File(filePath);

                float barPercent = 0F;
                long byteSum = 0;
                int byteRead = 0;

                final FileInputStream fis = new FileInputStream(this.img);
                final FileOutputStream fos = new FileOutputStream(fileTmp);
                final byte[] buffer = new byte[1024];
                while ((byteRead = fis.read(buffer)) != -1) {
                    byteSum += byteRead;
                    session.setAttribute("byteSum", byteSum);
                    barPercent = Float.parseFloat(byteSum + "") / Float.parseFloat(fileSize + "");
                    session.setAttribute("barPercent", barPercent);
                    fos.write(buffer, 0, byteRead);
                }
                IOUtils.closeQuietly(fis);
                IOUtils.closeQuietly(fos);

                this.dto.setImageName(fileName);
            }

            this.loginUserService.profileUpdateUser(this.dto);

            final SimpleAppMessage messageMsg = new SimpleAppMessage("EMS01001").format();
            this.msg = messageMsg.getMesg();

        } catch (final BusinessCheckException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final OptimisticLockStolenException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        } catch (final IOException e) {
            e.printStackTrace();
            this.msg = e.getMessage();
        }

        return "toProfileEditInit";
    }

    @Action("changePwdInit")
    public String changePwdInit() {
        this.columnIds = 1;
        this.bigColumList = this.kbnService.getBigColum();
        return "changePwdInit";
    }

    @Action("changePwd")
    public String changePwd() {
        try {
            this.loginUser = this.loginUserService.getUserByUserId(this.getSessionUser().getUserId());

            this.image = this.loginUser.getImageName();

            ActionContext.getContext().getSession().put("image", this.image);

            ActionContext.getContext().getSession().remove("user");
            ActionContext.getContext().getSession().remove("expire");
            ActionContext.getContext().getSession().remove("reset");
            this.msg = this.loginUserService.validatePwd(this.dto);
            if (this.msg != null) {
                return changePwdInit();
            }
            this.loginUserService.updatePwd(this.dto);

        } catch (final ObjectNotFoundException e) {
            this.setErrorMessages(e);
        } catch (final OptimisticLockStolenException e) {
            this.setErrorMessages(e);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        }
        return "logout";
    }

    /**
     * 角色备份
     * 
     * @return String
     */
    @SuppressWarnings({"hiding", "unchecked"})
    @Action("backupUser")
    public String backupUser() {
        init();
        try {
            this.dto = this.loginUserService.getById(this.dto.getUserId());
            final List<LoginUserDto> loginUserList = this.loginUserService.selectAll();
            final String canShowUserCode = BackUpUserUtil.canShowUserCode(this.dto.getRoles(), this.isKSHZ); // 获取备份用户列表
            final String backUpUserCode = BackUpUserUtil.getBackUpUserCode(this.dto.getRoles(), this.isKSHZ); // 获取备份用户Code
            if (!canShowUserCode.isEmpty()) { // 筛选用户可选择分配权限在用户
                this.userList.clear();
                for (final LoginUserDto loginUserDto : loginUserList) {
                    for (final Role role : loginUserDto.getRoles()) {
                        if (canShowUserCode.indexOf("|" + role.getCode() + "|") > -1) {
                            this.userList.add(loginUserDto);
                            break;
                        }
                    }
                }
            }

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "backupUser";
    }

    private byte[] fileToByte(final File tmpFile) {
        byte len[];
        try {
            final FileInputStream in = new FileInputStream(tmpFile);
            final BufferedInputStream inFile = new BufferedInputStream(in);
            final int a = Integer.parseInt(String.valueOf(tmpFile.length()));
            len = new byte[a];
            while (inFile.read(len) != -1) {
            }
        } catch (final Exception e) {
            throw new java.lang.RuntimeException(e);
        }

        return len;
    }

    /**
     * getter for msg.
     * 
     * @return msg
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * setter for msg.
     * 
     * @param aMsg msg
     */
    public void setMsg(final String aMsg) {
        this.msg = aMsg;
    }

    /**
     * @see com.soltoris.sisqp.master.presentation.action.CommonMasterMaintenanceAction#setDto(com.soltoris.sisqp.master.service.dto.MasterEntryDto)
     */

    @Override
    public void setDto(final LoginUserDto aDto) {
        this.dto = aDto;
    }

    /**
     * @see com.soltoris.sisqp.master.presentation.action.CommonMasterMaintenanceAction#getDto()
     */

    @Override
    public LoginUserDto getDto() {
        return this.dto;
    }

    /**
     * @see com.soltoris.sisqp.master.presentation.action.CommonMasterMaintenanceAction#checkVersionno(java.lang.String,
     *      com.soltoris.sisqp.master.service.dto.MasterEntryDto)
     */

    @Override
    protected void checkVersionno(final String aMstKey, final LoginUserDto aDto) throws OptimisticLockStolenException {
    }

    /**
     * getter for userList.
     * 
     * @return userList
     */
    public List<LoginUserDto> getUserList() {
        return this.userList;
    }

    /**
     * setter for userList.
     * 
     * @param aUserList userList
     */
    public void setUserList(final List<LoginUserDto> aUserList) {
        this.userList = aUserList;
    }

    /**
     * getter for roleList.
     * 
     * @return roleList
     */
    public List<RoleDto> getRoleList() {
        return this.roleList;
    }

    /**
     * setter for roleList.
     * 
     * @param aRoleList roleList
     */
    public void setRoleList(final List<RoleDto> aRoleList) {
        this.roleList = aRoleList;
    }

    /**
     * getter for namespace.
     * 
     * @return namespace
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * setter for namespace.
     * 
     * @param aNamespace namespace
     */
    public void setNamespace(final String aNamespace) {
        this.namespace = aNamespace;
    }

    /**
     * getter for departmentList.
     * 
     * @return departmentList
     */
    public List<DepartmentDto> getDepartmentList() {
        return this.departmentList;
    }

    /**
     * setter for departmentList.
     * 
     * @param aDepartmentList departmentList
     */
    public void setDepartmentList(final List<DepartmentDto> aDepartmentList) {
        this.departmentList = aDepartmentList;
    }

    /**
     * getter for kbnList.
     * 
     * @return kbnList
     */
    public List<KbnDto> getKbnList() {
        return this.kbnList;
    }

    /**
     * setter for kbnList.
     * 
     * @param aKbnList kbnList
     */
    public void setKbnList(final List<KbnDto> aKbnList) {
        this.kbnList = aKbnList;
    }

    /**
     * getter for companyId.
     * 
     * @return companyId
     */
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * setter for companyId.
     * 
     * @param aCompanyId companyId
     */
    public void setCompanyId(final Long aCompanyId) {
        this.companyId = aCompanyId;
    }

    /**
     * getter for departmentId.
     * 
     * @return departmentId
     */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * setter for departmentId.
     * 
     * @param aDepartmentId departmentId
     */
    public void setDepartmentId(final Long aDepartmentId) {
        this.departmentId = aDepartmentId;
    }

    /**
     * getter for backUserList.
     * 
     * @return backUserList
     */
    public List<LoginUserDto> getBackUserList() {
        return this.backUserList;
    }

    /**
     * setter for backUserList.
     * 
     * @param aBackUserList backUserList
     */
    public void setBackUserList(final List<LoginUserDto> aBackUserList) {
        this.backUserList = aBackUserList;
    }

    /**
     * getter for userId.
     * 
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * setter for userId.
     * 
     * @param aUserId userId
     */
    public void setUserId(final String aUserId) {
        this.userId = aUserId;
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

    public int getHasBackUpauth() {
        return this.hasBackUpauth;
    }

    public void setHasBackUpauth(final int aHasBackUpauth) {
        this.hasBackUpauth = aHasBackUpauth;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(final Role aRole) {
        this.role = aRole;
    }

    public LoginUserDto getRemoveDto() {
        return this.removeDto;
    }

    public void setRemoveDto(final LoginUserDto aRemoveDto) {
        this.removeDto = aRemoveDto;
    }

    public List<OsAudittrailDto> getLogList() {
        return this.logList;
    }

    public void setLogList(final List<OsAudittrailDto> aLogList) {
        this.logList = aLogList;
    }

    public Long getUserStatusId() {
        return this.userStatusId;
    }

    public void setUserStatusId(final Long aUserStatusId) {
        this.userStatusId = aUserStatusId;
    }

    public int getIsKSHZ() {
        return this.isKSHZ;
    }

    public void setIsKSHZ(final int aIsKSHZ) {
        this.isKSHZ = aIsKSHZ;
    }

    public List<LoginUserDto> getUsers() {
        return this.users;
    }

    public void setUsers(final List<LoginUserDto> aUsers) {
        this.users = aUsers;
    }

    public List<Company> getCompanyList() {
        return this.companyList;
    }

    public void setCompanyList(final List<Company> aCompanyList) {
        this.companyList = aCompanyList;
    }

    /**
     * getter for deptDto.
     * 
     * @return deptDto
     */
    public DepartmentDto getDeptDto() {
        return this.deptDto;
    }

    /**
     * setter for deptDto.
     * 
     * @param aDeptDto deptDto
     */
    public void setDeptDto(final DepartmentDto aDeptDto) {
        this.deptDto = aDeptDto;
    }

    /**
     * getter for companyDto.
     * 
     * @return companyDto
     */
    public CompanyDto getCompanyDto() {
        return this.companyDto;
    }

    /**
     * setter for companyDto.
     * 
     * @param aCompanyDto companyDto
     */
    public void setCompanyDto(final CompanyDto aCompanyDto) {
        this.companyDto = aCompanyDto;
    }

    /**
     * getter for bigColumList.
     * 
     * @return bigColumList
     */
    public List<KbnDto> getBigColumList() {
        return this.bigColumList;
    }

    /**
     * setter for bigColumList.
     * 
     * @param aBigColumList bigColumList
     */
    public void setBigColumList(final List<KbnDto> aBigColumList) {
        this.bigColumList = aBigColumList;
    }

    /**
     * getter for systemList.
     * 
     * @return systemList
     */
    public List<SubSystem> getSystemList() {
        return this.systemList;
    }

    /**
     * setter for systemList.
     * 
     * @param aSystemList systemList
     */
    public void setSystemList(final List<SubSystem> aSystemList) {
        this.systemList = aSystemList;
    }

    /**
     * getter for comList.
     * 
     * @return comList
     */
    public List<ArticleDto> getComList() {
        return this.comList;
    }

    /**
     * setter for comList.
     * 
     * @param aComList comList
     */
    public void setComList(final List<ArticleDto> aComList) {
        this.comList = aComList;
    }

    /**
     * getter for eightList.
     * 
     * @return eightList
     */
    public List<ArticleDto> getEightList() {
        return this.eightList;
    }

    /**
     * setter for eightList.
     * 
     * @param aEightList eightList
     */
    public void setEightList(final List<ArticleDto> aEightList) {
        this.eightList = aEightList;
    }

    /**
     * getter for noticedtoList.
     * 
     * @return noticedtoList
     */
    public List<NoticeDto> getNoticedtoList() {
        return this.noticedtoList;
    }

    /**
     * setter for noticedtoList.
     * 
     * @param aNoticedtoList noticedtoList
     */
    public void setNoticedtoList(final List<NoticeDto> aNoticedtoList) {
        this.noticedtoList = aNoticedtoList;
    }

    /**
     * getter for img.
     * 
     * @return img
     */
    public File getImg() {
        return this.img;
    }

    /**
     * setter for img.
     * 
     * @param aImg img
     */
    public void setImg(final File aImg) {
        this.img = aImg;
    }

    /**
     * getter for imgContentType.
     * 
     * @return imgContentType
     */
    public String getImgContentType() {
        return this.imgContentType;
    }

    /**
     * setter for imgContentType.
     * 
     * @param aImgContentType imgContentType
     */
    public void setImgContentType(final String aImgContentType) {
        this.imgContentType = aImgContentType;
    }

    /**
     * getter for imgFileName.
     * 
     * @return imgFileName
     */
    public String getImgFileName() {
        return this.imgFileName;
    }

    /**
     * setter for imgFileName.
     * 
     * @param aImgFileName imgFileName
     */
    public void setImgFileName(final String aImgFileName) {
        this.imgFileName = aImgFileName;
    }

    /**
     * getter for image.
     * 
     * @return image
     */
    public String getImage() {
        return this.image;
    }

    /**
     * setter for image.
     * 
     * @param aImage image
     */
    public void setImage(final String aImage) {
        this.image = aImage;
    }

    /**
     * getter for loginUser.
     * 
     * @return loginUser
     */
    public LoginUser getLoginUser() {
        return this.loginUser;
    }

    /**
     * setter for loginUser.
     * 
     * @param aLoginUser loginUser
     */
    public void setLoginUser(final LoginUser aLoginUser) {
        this.loginUser = aLoginUser;
    }

    /**
     * getter for columnIds.
     * 
     * @return columnIds
     */
    public int getColumnIds() {
        return this.columnIds;
    }

    /**
     * setter for columnIds.
     * 
     * @param aColumnIds columnIds
     */
    public void setColumnIds(final int aColumnIds) {
        this.columnIds = aColumnIds;
    }

}
